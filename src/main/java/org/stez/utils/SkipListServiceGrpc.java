package org.stez.utils;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * 服务定义
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: skiplist.proto")
public final class SkipListServiceGrpc {

  private SkipListServiceGrpc() {}

  public static final String SERVICE_NAME = "org.stez.server.SkipListService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.stez.utils.GetOptimalNodeRequest,
      org.stez.utils.OptimalNodeResponse> getGetOptimalNodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetOptimalNode",
      requestType = org.stez.utils.GetOptimalNodeRequest.class,
      responseType = org.stez.utils.OptimalNodeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.stez.utils.GetOptimalNodeRequest,
      org.stez.utils.OptimalNodeResponse> getGetOptimalNodeMethod() {
    io.grpc.MethodDescriptor<org.stez.utils.GetOptimalNodeRequest, org.stez.utils.OptimalNodeResponse> getGetOptimalNodeMethod;
    if ((getGetOptimalNodeMethod = SkipListServiceGrpc.getGetOptimalNodeMethod) == null) {
      synchronized (SkipListServiceGrpc.class) {
        if ((getGetOptimalNodeMethod = SkipListServiceGrpc.getGetOptimalNodeMethod) == null) {
          SkipListServiceGrpc.getGetOptimalNodeMethod = getGetOptimalNodeMethod = 
              io.grpc.MethodDescriptor.<org.stez.utils.GetOptimalNodeRequest, org.stez.utils.OptimalNodeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "org.stez.server.SkipListService", "GetOptimalNode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.stez.utils.GetOptimalNodeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.stez.utils.OptimalNodeResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SkipListServiceMethodDescriptorSupplier("GetOptimalNode"))
                  .build();
          }
        }
     }
     return getGetOptimalNodeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.stez.utils.UpdateNodeStatusRequest,
      org.stez.utils.UpdateNodeStatusResponse> getUpdateNodeStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateNodeStatus",
      requestType = org.stez.utils.UpdateNodeStatusRequest.class,
      responseType = org.stez.utils.UpdateNodeStatusResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.stez.utils.UpdateNodeStatusRequest,
      org.stez.utils.UpdateNodeStatusResponse> getUpdateNodeStatusMethod() {
    io.grpc.MethodDescriptor<org.stez.utils.UpdateNodeStatusRequest, org.stez.utils.UpdateNodeStatusResponse> getUpdateNodeStatusMethod;
    if ((getUpdateNodeStatusMethod = SkipListServiceGrpc.getUpdateNodeStatusMethod) == null) {
      synchronized (SkipListServiceGrpc.class) {
        if ((getUpdateNodeStatusMethod = SkipListServiceGrpc.getUpdateNodeStatusMethod) == null) {
          SkipListServiceGrpc.getUpdateNodeStatusMethod = getUpdateNodeStatusMethod = 
              io.grpc.MethodDescriptor.<org.stez.utils.UpdateNodeStatusRequest, org.stez.utils.UpdateNodeStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "org.stez.server.SkipListService", "UpdateNodeStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.stez.utils.UpdateNodeStatusRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.stez.utils.UpdateNodeStatusResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SkipListServiceMethodDescriptorSupplier("UpdateNodeStatus"))
                  .build();
          }
        }
     }
     return getUpdateNodeStatusMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SkipListServiceStub newStub(io.grpc.Channel channel) {
    return new SkipListServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SkipListServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SkipListServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SkipListServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SkipListServiceFutureStub(channel);
  }

  /**
   * <pre>
   * 服务定义
   * </pre>
   */
  public static abstract class SkipListServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 获取最优节点方法
     * </pre>
     */
    public void getOptimalNode(org.stez.utils.GetOptimalNodeRequest request,
        io.grpc.stub.StreamObserver<org.stez.utils.OptimalNodeResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetOptimalNodeMethod(), responseObserver);
    }

    /**
     * <pre>
     * 更新节点状态方法
     * </pre>
     */
    public void updateNodeStatus(org.stez.utils.UpdateNodeStatusRequest request,
        io.grpc.stub.StreamObserver<org.stez.utils.UpdateNodeStatusResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateNodeStatusMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetOptimalNodeMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.stez.utils.GetOptimalNodeRequest,
                org.stez.utils.OptimalNodeResponse>(
                  this, METHODID_GET_OPTIMAL_NODE)))
          .addMethod(
            getUpdateNodeStatusMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.stez.utils.UpdateNodeStatusRequest,
                org.stez.utils.UpdateNodeStatusResponse>(
                  this, METHODID_UPDATE_NODE_STATUS)))
          .build();
    }
  }

  /**
   * <pre>
   * 服务定义
   * </pre>
   */
  public static final class SkipListServiceStub extends io.grpc.stub.AbstractStub<SkipListServiceStub> {
    private SkipListServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SkipListServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SkipListServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SkipListServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取最优节点方法
     * </pre>
     */
    public void getOptimalNode(org.stez.utils.GetOptimalNodeRequest request,
        io.grpc.stub.StreamObserver<org.stez.utils.OptimalNodeResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetOptimalNodeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 更新节点状态方法
     * </pre>
     */
    public void updateNodeStatus(org.stez.utils.UpdateNodeStatusRequest request,
        io.grpc.stub.StreamObserver<org.stez.utils.UpdateNodeStatusResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateNodeStatusMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * 服务定义
   * </pre>
   */
  public static final class SkipListServiceBlockingStub extends io.grpc.stub.AbstractStub<SkipListServiceBlockingStub> {
    private SkipListServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SkipListServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SkipListServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SkipListServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取最优节点方法
     * </pre>
     */
    public org.stez.utils.OptimalNodeResponse getOptimalNode(org.stez.utils.GetOptimalNodeRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetOptimalNodeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 更新节点状态方法
     * </pre>
     */
    public org.stez.utils.UpdateNodeStatusResponse updateNodeStatus(org.stez.utils.UpdateNodeStatusRequest request) {
      return blockingUnaryCall(
          getChannel(), getUpdateNodeStatusMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * 服务定义
   * </pre>
   */
  public static final class SkipListServiceFutureStub extends io.grpc.stub.AbstractStub<SkipListServiceFutureStub> {
    private SkipListServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SkipListServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SkipListServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SkipListServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 获取最优节点方法
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.stez.utils.OptimalNodeResponse> getOptimalNode(
        org.stez.utils.GetOptimalNodeRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetOptimalNodeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * 更新节点状态方法
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.stez.utils.UpdateNodeStatusResponse> updateNodeStatus(
        org.stez.utils.UpdateNodeStatusRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateNodeStatusMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_OPTIMAL_NODE = 0;
  private static final int METHODID_UPDATE_NODE_STATUS = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SkipListServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SkipListServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_OPTIMAL_NODE:
          serviceImpl.getOptimalNode((org.stez.utils.GetOptimalNodeRequest) request,
              (io.grpc.stub.StreamObserver<org.stez.utils.OptimalNodeResponse>) responseObserver);
          break;
        case METHODID_UPDATE_NODE_STATUS:
          serviceImpl.updateNodeStatus((org.stez.utils.UpdateNodeStatusRequest) request,
              (io.grpc.stub.StreamObserver<org.stez.utils.UpdateNodeStatusResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SkipListServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SkipListServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.stez.utils.SkipListProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SkipListService");
    }
  }

  private static final class SkipListServiceFileDescriptorSupplier
      extends SkipListServiceBaseDescriptorSupplier {
    SkipListServiceFileDescriptorSupplier() {}
  }

  private static final class SkipListServiceMethodDescriptorSupplier
      extends SkipListServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SkipListServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SkipListServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SkipListServiceFileDescriptorSupplier())
              .addMethod(getGetOptimalNodeMethod())
              .addMethod(getUpdateNodeStatusMethod())
              .build();
        }
      }
    }
    return result;
  }
}
