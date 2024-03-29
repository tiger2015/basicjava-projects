// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: netty_rpc.proto

package com.tiger.rpc.common.protos;

public interface RpcRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:test.RpcRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string clazz = 1;</code>
   * @return The clazz.
   */
  String getClazz();
  /**
   * <code>string clazz = 1;</code>
   * @return The bytes for clazz.
   */
  com.google.protobuf.ByteString
      getClazzBytes();

  /**
   * <code>string method = 2;</code>
   * @return The method.
   */
  String getMethod();
  /**
   * <code>string method = 2;</code>
   * @return The bytes for method.
   */
  com.google.protobuf.ByteString
      getMethodBytes();

  /**
   * <code>repeated .test.RpcParam params = 3;</code>
   */
  java.util.List<RpcParam>
      getParamsList();
  /**
   * <code>repeated .test.RpcParam params = 3;</code>
   */
  RpcParam getParams(int index);
  /**
   * <code>repeated .test.RpcParam params = 3;</code>
   */
  int getParamsCount();
  /**
   * <code>repeated .test.RpcParam params = 3;</code>
   */
  java.util.List<? extends RpcParamOrBuilder>
      getParamsOrBuilderList();
  /**
   * <code>repeated .test.RpcParam params = 3;</code>
   */
  RpcParamOrBuilder getParamsOrBuilder(
      int index);
}
