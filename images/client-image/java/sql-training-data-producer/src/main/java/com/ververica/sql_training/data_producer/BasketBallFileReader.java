package com.ververica.sql_training.data_producer;

import com.ververica.sql_training.data_producer.json_serde.JsonDeserializer;
import com.ververica.sql_training.data_producer.records.BasketBallRecord;
import com.ververica.sql_training.data_producer.records.TaxiRecord;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

public class BasketBallFileReader implements Supplier<BasketBallRecord> {

    private final Iterator<BasketBallRecord> records;
    private final String filePath;

    public BasketBallFileReader(String filePath, Class<? extends BasketBallRecord> recordClazz) throws IOException {

        this.filePath = filePath;
        JsonDeserializer<?> deserializer = new JsonDeserializer<>(recordClazz);
        try {

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new GZIPInputStream(new FileInputStream(filePath)), StandardCharsets.UTF_8));

            Stream<String> lines = reader.lines().sequential();
            records = lines.map(l -> (BasketBallRecord) deserializer.parseFromString(l)).iterator();

        } catch (IOException e) {
            throw new IOException("Error reading TaxiRecords from file: " + filePath, e);
        }
    }

    @Override
    public BasketBallRecord get() {

        if (records.hasNext()) {
            return records.next();
        } else {
            throw new NoSuchElementException("All records read from " + filePath);
        }
    }
}
