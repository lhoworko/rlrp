package io.github.rlrp;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;

public class BinaryString {

    private BinaryString() {}

    public static String read(ByteArrayInputStream stream) {
        // Encoded string includes '\0'. Don't include it in returned string.
        int length = BinaryInteger.read32(stream, true) - 1;

        ByteBuffer buffer = ByteBuffer.allocate(length);
        for (int i = 0; i < length; i++) {
            buffer.put((byte)stream.read());
        }

        buffer.position(0);
        return new String(buffer.array());
    }
}
