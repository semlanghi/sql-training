package com.ververica.sql_training.data_producer;

import com.ververica.sql_training.data_producer.records.BasketBallRecord;
import com.ververica.sql_training.data_producer.records.TaxiRecord;

import java.time.Instant;
import java.util.function.UnaryOperator;

public class BasketBallDelayer implements UnaryOperator<BasketBallRecord> {

    // the speedup factor
    private final double speedUp;
    // the machine time when the delayer was instantiated.
    private final long startTime;
    // the event time of the first processed record
    private long startEventTime = -1;
    // the event time of the last processed record
    private long prevEventTime;
    // a counter to sync emission on machine time
    private int syncCounter = 0;

    public BasketBallDelayer() {
        this(1.0);
    }

    public BasketBallDelayer(double speedUp) {
        this.speedUp = speedUp;
        this.startTime = Instant.now().toEpochMilli();
    }

    @Override
    public BasketBallRecord apply(BasketBallRecord record) {
        long thisEventTime = record.getEventTime().getTime();

        if (startEventTime < 0) {
            // remember event time of first record
            startEventTime = thisEventTime;
        } else {
            // how much time to wait between the previous and this record
            long gapTime = (long) ((thisEventTime - prevEventTime) / speedUp);

            if (gapTime > 0 || syncCounter > 1000) {
                // syncing on machine time at least every 1000 records

                // compute how many machine time ms to wait before emitting the record
                long currentTime = Instant.now().toEpochMilli();
                long targetEmitTime = (long) ((thisEventTime - startEventTime) / speedUp) + startTime;
                long waitTime = targetEmitTime - currentTime;

                // wait if necessary
                if (waitTime > 0) {
                    try {
                        Thread.sleep(waitTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // reset sync counter
                syncCounter = 0;
            } else {
                // we emitted without syncing on time. Increment counter.
                syncCounter++;
            }
        }

        this.prevEventTime = thisEventTime;

        return record;
    }
}
