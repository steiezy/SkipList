// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: skiplist.proto

package org.stez.utils;

public interface UpdateNodeStatusRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:org.stez.server.UpdateNodeStatusRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * 节点名称
   * </pre>
   *
   * <code>string node_name = 1;</code>
   */
  java.lang.String getNodeName();
  /**
   * <pre>
   * 节点名称
   * </pre>
   *
   * <code>string node_name = 1;</code>
   */
  com.google.protobuf.ByteString
      getNodeNameBytes();

  /**
   * <pre>
   * 评分
   * </pre>
   *
   * <code>int32 score = 2;</code>
   */
  int getScore();
}
