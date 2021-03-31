package zyj.test.flink.kafka;


import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

public class FlinkKafka {
    public static void main(String[] args) {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "10.0.100.138:9092");
        properties.setProperty("group.id", "test");
        FlinkKafkaConsumer<String> kafkaConsumer =
                new FlinkKafkaConsumer<>("zyj_test", new SimpleStringSchema(), properties);
//        DataStreamSource<String> outDataStreamSource = environment.addSource(kafkaConsumer);

    }
}
