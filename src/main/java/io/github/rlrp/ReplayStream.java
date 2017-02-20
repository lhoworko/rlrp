package io.github.rlrp;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ReplayStream extends ByteArrayInputStream {

    public ReplayStream(byte[] buf) {
        super(buf);
    }

    public Integer readInteger32(boolean littleEndian) {
        return readInteger(4, littleEndian);
    }

    public Integer readInteger8(boolean littleEndian) {
        return readInteger(1, littleEndian);
    }

    public String readString() {
        int length = readInteger32(true);
        return readString(length);
    }

    public String readString(int length) {
        ByteBuffer buffer = ByteBuffer.allocate(length - 1);
        for (int i = 0; i < length - 1; i++) {
            buffer.put((byte)this.read());
        }

        // Encoded string includes '\0'. Don't include it in returned string.
        this.skip(1);

        buffer.position(0);
        return new String(buffer.array());
    }

    private Integer readInteger(int length, boolean littleEndian) {
        ByteBuffer buffer = ByteBuffer.allocate(length);
        buffer.order(littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);

        for (int i = 0; i < length; i++) {
            buffer.put((byte)this.read());
        }

        buffer.position(0);
        return buffer.getInt();
    }
}
