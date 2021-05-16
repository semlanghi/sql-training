package com.ververica.sql_training.data_producer;

import com.ververica.sql_training.data_producer.json_serde.JsonSerializer;
import com.ververica.sql_training.data_producer.records.BasketBallRecord;
import com.ververica.sql_training.data_producer.records.TaxiRecord;

import java.util.function.Consumer;

public class BasketBallConsolePrinter implements Consumer<BasketBallRecord> {

    private final JsonSerializer<BasketBallRecord> serializer = new JsonSerializer<>();
    @Override
    public void accept(BasketBallRecord basketBallRecord) {
        String jsonString = serializer.toJSONString(basketBallRecord);
        System.out.println(jsonString);
    }
}
