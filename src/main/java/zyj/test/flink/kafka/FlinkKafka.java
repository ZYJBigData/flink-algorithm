package zyj.test.flink.kafka;


import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

public class FlinkKafka {
    public static void main(String[] args) {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "10.0.100.138:9092");
        properties.setProperty("group.id", "test");
        new FlinkKafkaConsumer<>("zyj-test", new SimpleStringSchema(), properties);
        DataStream<String> stream = environment
                .addSource(new FlinkKafkaConsumer<>("zyj-test", new SimpleStringSchema(), properties));

    }
}
