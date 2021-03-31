package zyj.test.flink.test;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.util.Collector;

/**
 * @author zhangyingjie
 * 1.处理单个元素
 * 2.访问时间戳
 */
public class FunctionProcessDemo {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //在flink1.2版本中已经废弃 默认就是事件时间
//        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        //设置并星都
        env.setParallelism(1);
        DataStreamSource<Tuple2<String, Integer>> dataStreamSource = env.addSource(new SourceFunction<Tuple2<String, Integer>>() {
            @Override
            public void run(SourceContext<Tuple2<String, Integer>> ctx) throws Exception {
                for (int i = 1; i < 4; i++) {
                    String name = "name" + i;
                    Integer value = i;
                    long timeStamp = System.currentTimeMillis();

                    System.out.println(String.format("source,%s.%d,%d", name, value, timeStamp));
                    // 发射元素，并且带上时间戳
                    ctx.collectWithTimestamp(new Tuple2<>(name, value), timeStamp);

                    // 为了让每个元素的时间戳不一样，每次发射延迟10毫秒
                    Thread.sleep(10);
                }
            }

            @Override
            public void cancel() {

            }
        });

        SingleOutputStreamOperator<String> process =
                dataStreamSource.process(new ProcessFunction<Tuple2<String, Integer>, String>() {
                    @Override
                    public void processElement(Tuple2<String, Integer> value, Context ctx, Collector<String> out) throws Exception {
                        //f1字段为奇数的元素不会进入下一个算子
                        if (0 == value.f1 % 2) {
                            out.collect(String.format("processElement,%s,%d %d \n"
                                    , value.f0, value.f1, ctx.timestamp()));
                        }
                    }
                });
        // 打印结果，证明每个元素的timestamp确实可以在processFunction中取得
        process.print();

        env.execute("processFunction demo: simple");
    }
}
