package io.github.rlrp.binaryreader;

public class BinaryInteger8 extends BinaryPrimative<Integer> {
    public BinaryInteger8(byte[] bytes, int seekLocation) {
        super(bytes, seekLocation, true, 1);
    }

    public Integer read() {
        return (int)getByteBuffer().get();
    }

    public String toString() {
        return String.format("%d", (int)getByteBuffer().get());
    }
}

