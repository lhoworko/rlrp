package io.github.rlrp.binaryreader;

public class BinaryString extends BinaryPrimative<String> {
    public BinaryString(byte[] bytes, int seekLocation) {
        super(bytes, seekLocation, false, 32, 126);
    }

    public String read() {
        return toString();
    }

    public String toString() {
        return new String(getByteBuffer().array());
    }
}
