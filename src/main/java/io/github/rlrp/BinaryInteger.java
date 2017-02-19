package io.github.rlrp;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BinaryInteger {
    private BinaryInteger() {}

    private static Integer read(ByteArrayInputStream stream, int length, boolean littleEndian) {
        ByteBuffer buffer = ByteBuffer.allocate(length);
        buffer.order(littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);

        for (int i = 0; i < length; i++) {
            buffer.put((byte)stream.read());
        }

        buffer.position(0);
        return buffer.getInt();
    }

    public static Integer read32(ByteArrayInputStream stream, boolean littleEndian) {
        return read(stream, 4, littleEndian);
    }

    public static Integer read8(ByteArrayInputStream stream, boolean littleEndian) {
        return read(stream, 1, littleEndian);
    }

}
