package zyj.test.flink.test;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordCountLocal {
    // nc -l 9000 开启一个socket 才能运行这个脚本
    final static Logger log = LoggerFactory.getLogger(WordCountLocal.class);

    public static void main(String[] args) throws Exception {
        //getExecutionEnvironment 如果程序是在idea等中独立运行的话 则返回的是本地环境的上下文
        // 此方法如果是通过命令行的方式 flink run 获取的是远程环境的上下文
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> text = env.socketTextStream("localhost", 9000, "\n");
        SingleOutputStreamOperator<WordWithCount> words = text.flatMap((FlatMapFunction<String, WordWithCount>) (value, out) -> {
            for (String word : value.split("\\s")) {
                out.collect(new WordWithCount(word, 1L));
            }
        }).keyBy("word")
                .timeWindow(Time.seconds(5), Time.seconds(1))
                .reduce((ReduceFunction<WordWithCount>) (w1, w2) -> new WordWithCount(w1.word, w1.count + w2.count));
        words.print().setParallelism(1);
        env.execute("Socket Window WordCount");
    }

    public static class WordWithCount {
        public String word;
        public long count;

        public WordWithCount() {
        }

        public WordWithCount(String word, long count) {
            this.word = word;
            this.count = count;
        }

        @Override
        public String toString() {
            return "WordWithCount{" +
                    "word='" + word + '\'' +
                    ", count=" + count +
                    '}';
        }
    }

}
