package org.stez.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.stez.skiplist.SkipList;
import org.stez.utils.*;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

public class SkipListServer {
    protected static int SERVER_PORT = 8888;

    private static final Logger logger = Logger.getLogger(SkipListServer.class.getName());

    private Server server;


    private SkipList skipList; // 添加 SkipList 实例

    // 构造函数中初始化 SkipList
    public SkipListServer() {
        this.skipList = new SkipList(); // TODO:直接加载历史数据
    }

    /**
     * 启动服务
     *
     * @param port
     * @throws IOException
     */
    private void start(int port) throws IOException {
        server = ServerBuilder.forPort(port).addService(new SkipListService(skipList)).build().start();
        logger.log(Level.INFO, "服务已经启动,监听端口：" + port);
        Runtime.getRuntime()
                .addShutdownHook(
                        new Thread(
                                () -> {
                                    logger.log(Level.WARNING, "监听到JVM停止,正在关闭GRPC服务....");
                                    SkipListServer.this.stop();
                                    logger.log(Level.WARNING, "服务已经停止...");
                                }));
    }

    /**
     * 关闭服务
     */
    public void stop() {
        Optional.of(server).map(s -> s.shutdown()).orElse(null);
    }

    /**
     * 循环运行服务,封锁停止
     *
     * @throws InterruptedException
     */
    public void blockUnitShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * 程序的主运行窗口
     *
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        SkipListServer service = new SkipListServer();
        service.start(SERVER_PORT);
        service.blockUnitShutdown();
    }

    /**
     * 实现的服务类
     */
    static class SkipListService extends SkipListServiceGrpc.SkipListServiceImplBase {

        private final SkipList skipList;

        SkipListService(SkipList skipList) {
            this.skipList = skipList;
        }

        @Override
        public void getOptimalNode(org.stez.utils.GetOptimalNodeRequest request,
                                   io.grpc.stub.StreamObserver<org.stez.utils.OptimalNodeResponse> responseObserver) {
            String optimalNode = skipList.getOptimalNode(request.getMinScore());
            OptimalNodeResponse response = OptimalNodeResponse.newBuilder().setNodeName(optimalNode).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void updateNodeStatus(org.stez.utils.UpdateNodeStatusRequest request,
                                     io.grpc.stub.StreamObserver<org.stez.utils.UpdateNodeStatusResponse> responseObserver) {
            String nodeName = request.getNodeName();
            int score = request.getScore();
            boolean result = skipList.updateNodeStatus(nodeName, score);
            skipList.printTree();
            UpdateNodeStatusResponse response = UpdateNodeStatusResponse.newBuilder().setSuccess(result).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
