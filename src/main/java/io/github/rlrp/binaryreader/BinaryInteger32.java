package io.github.rlrp.binaryreader;

public class BinaryInteger32 extends BinaryPrimative<Integer>{
    public BinaryInteger32(byte[] bytes, int seekLocation) {
        super(bytes, seekLocation, true, 4);
    }

    public Integer read() {
        return getByteBuffer().getInt();
    }

    public String toString() {
        return String.format("%d", getByteBuffer().getInt());
    }
}
