package zyj.test.flink.test;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

public class WordCountRemote {
    public static void main(String[] args) throws Exception {
        // 独立运行 到远程的环境运行
        // 但是如果你flink run命令不能使用createRemoteEnvironment()这个函数，需要换成getExecutionEnvironment()
        //  ExecutionEnvironment environment=ExecutionEnvironment.createRemoteEnvironment();
        ExecutionEnvironment environment = ExecutionEnvironment.getExecutionEnvironment();
        DataSet<String> data = environment.readTextFile("hdfs://192.168.10.168:8020/flink/test/file/in.txt");

        data.filter(new FilterFunction<String>() {
            @Override
            public boolean filter(String value) {
                return value.startsWith("a");
            }
        })
                .writeAsText("hdfs://192.168.10.168:8020/flink/test/file/out.txt");

        environment.execute();
    }
}
