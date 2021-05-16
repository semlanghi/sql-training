package com.ververica.sql_training.data_producer;

import com.ververica.sql_training.data_producer.records.*;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class BasketBallRecordProducer {

    public static void main(String[] args) throws InterruptedException {

        boolean areSuppliersConfigured = false;
        boolean areConsumersConfigured = false;

        Supplier<BasketBallRecord> passageSupplier = null;
        Supplier<BasketBallRecord> shotSupplier = null;
        Supplier<BasketBallRecord> substitutionSupplier = null;

        Consumer<BasketBallRecord> passageConsumer = null;
        Consumer<BasketBallRecord> shotConsumer = null;
        Consumer<BasketBallRecord> substitutionConsumer = null;

        double speedup = 1.0d;

        // parse arguments
        int argOffset = 0;
        while(argOffset < args.length) {

            String arg = args[argOffset++];
            switch (arg) {
                case "--input":
                    String source = args[argOffset++];
                    switch (source) {
                        case "file":
                            String basePath = args[argOffset++];
                            try {
                                passageSupplier = new BasketBallFileReader(basePath + "/passages.txt.gz", Passage.class);
                                shotSupplier = new BasketBallFileReader(basePath + "/shots.txt.gz", Shot.class);
                                substitutionSupplier = new BasketBallFileReader(basePath + "/substitutions.txt.gz", Substitution.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown input configuration");
                    }
                    areSuppliersConfigured = true;
                    break;
                case "--output":
                    String sink = args[argOffset++];
                    switch (sink) {
                        case "console":
                            passageConsumer = new BasketBallConsolePrinter();
                            shotConsumer = new BasketBallConsolePrinter();
                            substitutionConsumer = new BasketBallConsolePrinter();
                            break;
                        case "kafka":
                            String brokers = args[argOffset++];
                            passageConsumer = new BasketBallKafkaProducer("Passages", brokers);
                            shotConsumer = new BasketBallKafkaProducer("Shots", brokers);
                            substitutionConsumer = new BasketBallKafkaProducer("Substitutions", brokers);
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown output configuration");
                    }
                    areConsumersConfigured = true;
                    break;
                case "--speedup":
                    speedup = Double.parseDouble(args[argOffset++]);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown parameter");
            }
        }

        // check if we have a source and a sink
        if (!areSuppliersConfigured) {
            throw new IllegalArgumentException("Input sources were not properly configured.");
        }
        if (!areConsumersConfigured) {
            throw new IllegalArgumentException("Output sinks were not properly configured");
        }

        // create three threads for each record type
        Thread ridesFeeder = new Thread(new BasketBallRecordFeeder(passageSupplier, new BasketBallDelayer(speedup), passageConsumer));
        Thread faresFeeder = new Thread(new BasketBallRecordFeeder(shotSupplier, new BasketBallDelayer(speedup), shotConsumer));
        Thread driverChangesFeeder = new Thread(new BasketBallRecordFeeder(substitutionSupplier, new BasketBallDelayer(speedup), substitutionConsumer));

        // start emitting data
        ridesFeeder.start();
        faresFeeder.start();
        driverChangesFeeder.start();

        // wait for threads to complete
        ridesFeeder.join();
        faresFeeder.join();
        driverChangesFeeder.join();
    }

    public static class BasketBallRecordFeeder implements Runnable {

        private final Supplier<BasketBallRecord> source;
        private final BasketBallDelayer delayer;
        private final Consumer<BasketBallRecord> sink;

        BasketBallRecordFeeder(Supplier<BasketBallRecord> source, BasketBallDelayer delayer, Consumer<BasketBallRecord> sink) {
            this.source = source;
            this.delayer = delayer;
            this.sink = sink;
        }

        @Override
        public void run() {
            Stream.generate(source).sequential()
                    .map(delayer)
                    .forEachOrdered(sink);
        }

    }
}
