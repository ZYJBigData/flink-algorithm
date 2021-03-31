package zyj.test.flink.test;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyingjie
 * 1.实现旁路输出，对于一个DataStream来说，可以通过旁路输出到其他算子中去，而不影响原来的算子的处理
 * <p>
 * 数据发送到侧输出流只能从一个ProcessFunction中发出，你可以使用Context参数来发送数据到一个通过OutputTag标记的侧输出流中:
 */
public class FunctionProcessDemoSecond {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env =
                StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        // 定义OutputTag
        // 注意后面的{} 不写的话 会有报错
        //Exception in thread "main" org.apache.flink.api.common.functions.InvalidTypesException: Could not determine TypeInformation for the OutputTag type. The most common reason is forgetting to make the OutputTag an anonymous inner class. It is also not possible to use generic type variables with OutputTags, such as 'Tuple2<A, B>'.
        //	at org.apache.flink.util.OutputTag.<init>(OutputTag.java:68)
        //	at zyj.test.flink.test.FunctionProcessDemoSecond.main(FunctionProcessDemoSecond.java:24)


        final OutputTag<String> outputTag = new OutputTag<String>("side-output") {
        };

        List<Tuple2<String, Integer>> list = new ArrayList<>();
        list.add(new Tuple2<>("aaa", 1));
        list.add(new Tuple2<>("bbb", 2));
        list.add(new Tuple2<>("ccc", 3));

        //通过list创建DataStream
        DataStream<Tuple2<String, Integer>> fromCollectionStream =
                env.fromCollection(list);

        SingleOutputStreamOperator<String> mainDataStream = fromCollectionStream
                .process(new ProcessFunction<Tuple2<String, Integer>, String>() {
                    //所有元素都进入mainDataStream,f1字段
                    @Override
                    public void processElement(Tuple2<String, Integer> value, Context ctx, Collector<String> out) throws Exception {
                        //进入主流程的下一个算子
                        out.collect("main,name: " + value.f0 + ",value: " + value.f1);

                        //f1 字段为奇数元素进入sideOutput
                        if (1 == value.f1 % 2) {
                            ctx.output(outputTag, "side,name : " + value.f0 + ", value: " + value.f1);
                        }
                    }
                });

        //禁止chanin 这样可以在页面上看清楚原始的DAG
        mainDataStream.disableChaining();

        //获取旁路数据
        DataStream<String> sideOutput = mainDataStream.getSideOutput(outputTag);

        mainDataStream.print();
        sideOutput.print();

        env.execute("processfunction demo :sideoutput");
    }

}
