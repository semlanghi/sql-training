package com.ververica.sql_training.data_producer;

import com.ververica.sql_training.data_producer.json_serde.JsonSerializer;
import com.ververica.sql_training.data_producer.records.BasketBallRecord;
import com.ververica.sql_training.data_producer.records.TaxiRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;

import java.util.Properties;
import java.util.function.Consumer;

public class BasketBallKafkaProducer implements Consumer<BasketBallRecord> {

    private final String topic;
    private final org.apache.kafka.clients.producer.KafkaProducer<byte[], byte[]> producer;
    private final JsonSerializer<BasketBallRecord> serializer;

    public BasketBallKafkaProducer(String kafkaTopic, String kafkaBrokers) {
        this.topic = kafkaTopic;
        this.producer = new org.apache.kafka.clients.producer.KafkaProducer<>(createKafkaProperties(kafkaBrokers));
        this.serializer = new JsonSerializer<>();
    }

    @Override
    public void accept(BasketBallRecord record) {
        // serialize record as JSON
        byte[] data = serializer.toJSONBytes(record);
        // create producer record and publish to Kafka
        ProducerRecord<byte[], byte[]> kafkaRecord = new ProducerRecord<>(topic, data);
        producer.send(kafkaRecord);
    }

    /**
     * Create configuration properties for Kafka producer.
     *
     * @param brokers The brokers to connect to.
     * @return A Kafka producer configuration.
     */
    private static Properties createKafkaProperties(String brokers) {
        Properties kafkaProps = new Properties();
        kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getCanonicalName());
        kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getCanonicalName());
        return kafkaProps;
    }
}
