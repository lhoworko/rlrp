package io.github.rlrp;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.LinkedList;

public class ReplayStream extends ByteArrayInputStream {

    public ReplayStream(byte[] buf) {
        super(buf);
    }

    public Integer readInteger32(boolean littleEndian) {
        return  readBuffer(4, true).getInt();
    }

    public Long readLong() {
        return readBuffer(8, true).getLong();
    }

    public String readString() {
        int length = readInteger32(true);
        return readString(length);
    }

    public float readFloat() {
        return readBuffer(4, true).getFloat();
    }


    public String readString(int length) {
        ByteBuffer buffer = ByteBuffer.allocate(length - 1);
        for (int i = 0; i < length - 1; i++) {
            buffer.put((byte)this.read());
        }

        // Encoded string includes '\0'. Don't include it in returned string.
        this.skip(1);

        return getString(buffer);
    }


    public EnumProperty readEnum(int length) {
       return new EnumProperty(readString(), readString());
    }

    private ByteBuffer readBuffer(int length, boolean littleEndian) {
        ByteBuffer buffer = ByteBuffer.allocate(length);
        buffer.order(littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);

        for (int i = 0; i < length; i++) {
            buffer.put((byte)this.read());
        }

        buffer.position(0);
        return buffer;
    }

    private String getString(ByteBuffer buffer) {
        buffer.position(0);
        return new String(buffer.array(), Charset.forName("UTF-8"));
    }

    public Boolean readBoolean() {
        Integer byteValue = (int)readBuffer(1, true).get(0);
        return byteValue == 1;
    }
}
