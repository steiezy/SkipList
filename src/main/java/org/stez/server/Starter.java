package org.stez.server;

import org.stez.skiplist.SkipList;

public class Starter {
    public static void main(String[] args) throws Exception {
        SkipList skipList = new SkipList();
        // 启动 gRPC 服务
        SkipListServer grpcServer = new SkipListServer(skipList);
        grpcServer.start(SkipListServer.SERVER_PORT);

        // 启动 RocketMQ 消费者
        RocketMQConsumer rocketMQConsumer = new RocketMQConsumer(skipList);
        rocketMQConsumer.start();

        // 阻塞主线程，保持服务运行
        grpcServer.blockUntilShutdown();

        // 钩子函数，在 JVM 关闭时执行关闭操作
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                grpcServer.stop(); // 停止 gRPC 服务
                rocketMQConsumer.shutdown(); // 停止 RocketMQ 消费者
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Application is shutting down.");
        }));

        // 阻塞主线程，保持服务运行
        grpcServer.blockUntilShutdown();
    }
}
