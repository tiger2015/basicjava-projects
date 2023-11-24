// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: netty_rpc.proto

package com.tiger.rpc.common.protos;

/**
 * Protobuf type {@code test.RpcResponse}
 */
public final class RpcResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:test.RpcResponse)
    RpcResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use RpcResponse.newBuilder() to construct.
  private RpcResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private RpcResponse() {
    clazz_ = "";
    value_ = com.google.protobuf.ByteString.EMPTY;
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new RpcResponse();
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return NettyRpc.internal_static_test_RpcResponse_descriptor;
  }

  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return NettyRpc.internal_static_test_RpcResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            RpcResponse.class, Builder.class);
  }

  private int bitField0_;
  public static final int STATUS_FIELD_NUMBER = 1;
  private int status_;
  /**
   * <code>int32 status = 1;</code>
   * @return The status.
   */
  @Override
  public int getStatus() {
    return status_;
  }

  public static final int CLAZZ_FIELD_NUMBER = 2;
  private volatile Object clazz_;
  /**
   * <code>optional string clazz = 2;</code>
   * @return Whether the clazz field is set.
   */
  @Override
  public boolean hasClazz() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   * <code>optional string clazz = 2;</code>
   * @return The clazz.
   */
  @Override
  public String getClazz() {
    Object ref = clazz_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      clazz_ = s;
      return s;
    }
  }
  /**
   * <code>optional string clazz = 2;</code>
   * @return The bytes for clazz.
   */
  @Override
  public com.google.protobuf.ByteString
      getClazzBytes() {
    Object ref = clazz_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      clazz_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int VALUE_FIELD_NUMBER = 3;
  private com.google.protobuf.ByteString value_;
  /**
   * <code>optional bytes value = 3;</code>
   * @return Whether the value field is set.
   */
  @Override
  public boolean hasValue() {
    return ((bitField0_ & 0x00000002) != 0);
  }
  /**
   * <code>optional bytes value = 3;</code>
   * @return The value.
   */
  @Override
  public com.google.protobuf.ByteString getValue() {
    return value_;
  }

  private byte memoizedIsInitialized = -1;
  @Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (status_ != 0) {
      output.writeInt32(1, status_);
    }
    if (((bitField0_ & 0x00000001) != 0)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, clazz_);
    }
    if (((bitField0_ & 0x00000002) != 0)) {
      output.writeBytes(3, value_);
    }
    getUnknownFields().writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (status_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, status_);
    }
    if (((bitField0_ & 0x00000001) != 0)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, clazz_);
    }
    if (((bitField0_ & 0x00000002) != 0)) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(3, value_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof RpcResponse)) {
      return super.equals(obj);
    }
    RpcResponse other = (RpcResponse) obj;

    if (getStatus()
        != other.getStatus()) return false;
    if (hasClazz() != other.hasClazz()) return false;
    if (hasClazz()) {
      if (!getClazz()
          .equals(other.getClazz())) return false;
    }
    if (hasValue() != other.hasValue()) return false;
    if (hasValue()) {
      if (!getValue()
          .equals(other.getValue())) return false;
    }
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + STATUS_FIELD_NUMBER;
    hash = (53 * hash) + getStatus();
    if (hasClazz()) {
      hash = (37 * hash) + CLAZZ_FIELD_NUMBER;
      hash = (53 * hash) + getClazz().hashCode();
    }
    if (hasValue()) {
      hash = (37 * hash) + VALUE_FIELD_NUMBER;
      hash = (53 * hash) + getValue().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static RpcResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static RpcResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static RpcResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static RpcResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static RpcResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static RpcResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static RpcResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static RpcResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static RpcResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static RpcResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static RpcResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static RpcResponse parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(RpcResponse prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @Override
  protected Builder newBuilderForType(
      BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code test.RpcResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:test.RpcResponse)
      RpcResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return NettyRpc.internal_static_test_RpcResponse_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return NettyRpc.internal_static_test_RpcResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              RpcResponse.class, Builder.class);
    }

    // Construct using com.tiger.rpc.common.protos.RpcResponse.newBuilder()
    private Builder() {

    }

    private Builder(
        BuilderParent parent) {
      super(parent);

    }
    @Override
    public Builder clear() {
      super.clear();
      status_ = 0;

      clazz_ = "";
      bitField0_ = (bitField0_ & ~0x00000001);
      value_ = com.google.protobuf.ByteString.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000002);
      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return NettyRpc.internal_static_test_RpcResponse_descriptor;
    }

    @Override
    public RpcResponse getDefaultInstanceForType() {
      return RpcResponse.getDefaultInstance();
    }

    @Override
    public RpcResponse build() {
      RpcResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public RpcResponse buildPartial() {
      RpcResponse result = new RpcResponse(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      result.status_ = status_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        to_bitField0_ |= 0x00000001;
      }
      result.clazz_ = clazz_;
      if (((from_bitField0_ & 0x00000002) != 0)) {
        to_bitField0_ |= 0x00000002;
      }
      result.value_ = value_;
      result.bitField0_ = to_bitField0_;
      onBuilt();
      return result;
    }

    @Override
    public Builder clone() {
      return super.clone();
    }
    @Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return super.setField(field, value);
    }
    @Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return super.addRepeatedField(field, value);
    }
    @Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof RpcResponse) {
        return mergeFrom((RpcResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(RpcResponse other) {
      if (other == RpcResponse.getDefaultInstance()) return this;
      if (other.getStatus() != 0) {
        setStatus(other.getStatus());
      }
      if (other.hasClazz()) {
        bitField0_ |= 0x00000001;
        clazz_ = other.clazz_;
        onChanged();
      }
      if (other.hasValue()) {
        setValue(other.getValue());
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @Override
    public final boolean isInitialized() {
      return true;
    }

    @Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {
              status_ = input.readInt32();

              break;
            } // case 8
            case 18: {
              clazz_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000001;
              break;
            } // case 18
            case 26: {
              value_ = input.readBytes();
              bitField0_ |= 0x00000002;
              break;
            } // case 26
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }
    private int bitField0_;

    private int status_ ;
    /**
     * <code>int32 status = 1;</code>
     * @return The status.
     */
    @Override
    public int getStatus() {
      return status_;
    }
    /**
     * <code>int32 status = 1;</code>
     * @param value The status to set.
     * @return This builder for chaining.
     */
    public Builder setStatus(int value) {
      
      status_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 status = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearStatus() {
      
      status_ = 0;
      onChanged();
      return this;
    }

    private Object clazz_ = "";
    /**
     * <code>optional string clazz = 2;</code>
     * @return Whether the clazz field is set.
     */
    public boolean hasClazz() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <code>optional string clazz = 2;</code>
     * @return The clazz.
     */
    public String getClazz() {
      Object ref = clazz_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        clazz_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <code>optional string clazz = 2;</code>
     * @return The bytes for clazz.
     */
    public com.google.protobuf.ByteString
        getClazzBytes() {
      Object ref = clazz_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        clazz_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>optional string clazz = 2;</code>
     * @param value The clazz to set.
     * @return This builder for chaining.
     */
    public Builder setClazz(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
      clazz_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional string clazz = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearClazz() {
      bitField0_ = (bitField0_ & ~0x00000001);
      clazz_ = getDefaultInstance().getClazz();
      onChanged();
      return this;
    }
    /**
     * <code>optional string clazz = 2;</code>
     * @param value The bytes for clazz to set.
     * @return This builder for chaining.
     */
    public Builder setClazzBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      bitField0_ |= 0x00000001;
      clazz_ = value;
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString value_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>optional bytes value = 3;</code>
     * @return Whether the value field is set.
     */
    @Override
    public boolean hasValue() {
      return ((bitField0_ & 0x00000002) != 0);
    }
    /**
     * <code>optional bytes value = 3;</code>
     * @return The value.
     */
    @Override
    public com.google.protobuf.ByteString getValue() {
      return value_;
    }
    /**
     * <code>optional bytes value = 3;</code>
     * @param value The value to set.
     * @return This builder for chaining.
     */
    public Builder setValue(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
      value_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional bytes value = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearValue() {
      bitField0_ = (bitField0_ & ~0x00000002);
      value_ = getDefaultInstance().getValue();
      onChanged();
      return this;
    }
    @Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:test.RpcResponse)
  }

  // @@protoc_insertion_point(class_scope:test.RpcResponse)
  private static final RpcResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new RpcResponse();
  }

  public static RpcResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<RpcResponse>
      PARSER = new com.google.protobuf.AbstractParser<RpcResponse>() {
    @Override
    public RpcResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<RpcResponse> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<RpcResponse> getParserForType() {
    return PARSER;
  }

  @Override
  public RpcResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
