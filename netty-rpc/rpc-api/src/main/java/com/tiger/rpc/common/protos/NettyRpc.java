// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: netty_rpc.proto

package com.tiger.rpc.common.protos;

public final class NettyRpc {
  private NettyRpc() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_test_RpcRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_test_RpcRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_test_RpcParam_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_test_RpcParam_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_test_RpcResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_test_RpcResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\017netty_rpc.proto\022\004test\"K\n\nRpcRequest\022\r\n" +
      "\005clazz\030\001 \001(\t\022\016\n\006method\030\002 \001(\t\022\036\n\006params\030\003" +
      " \003(\0132\016.test.RpcParam\"\'\n\010RpcParam\022\014\n\004type" +
      "\030\001 \001(\t\022\r\n\005value\030\002 \001(\014\"Y\n\013RpcResponse\022\016\n\006" +
      "status\030\001 \001(\005\022\022\n\005clazz\030\002 \001(\tH\000\210\001\001\022\022\n\005valu" +
      "e\030\003 \001(\014H\001\210\001\001B\010\n\006_clazzB\010\n\006_valueB\037\n\033com." +
      "tiger.rpc.common.protosP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_test_RpcRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_test_RpcRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_test_RpcRequest_descriptor,
        new String[] { "Clazz", "Method", "Params", });
    internal_static_test_RpcParam_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_test_RpcParam_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_test_RpcParam_descriptor,
        new String[] { "Type", "Value", });
    internal_static_test_RpcResponse_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_test_RpcResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_test_RpcResponse_descriptor,
        new String[] { "Status", "Clazz", "Value", "Clazz", "Value", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}