package io.github.rlrp.binaryreader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;

public abstract class BinaryPrimative<T> {
    private final byte[] m_bytes;
    private final int m_seekLocation;
    private final boolean m_littleEndian;
    private final int m_byteCount;
    private final Byte[] m_sentinel;
    private final int m_minByteValue;
    private final int m_maxByteValue;

    public BinaryPrimative(byte[] bytes, int seekLocation, boolean littleEndian, int byteCount) {
        m_bytes = bytes;
        m_seekLocation = seekLocation;
        m_littleEndian = littleEndian;
        m_byteCount = byteCount;
        m_sentinel = new Byte[] {};
        m_minByteValue = Integer.MIN_VALUE;
        m_maxByteValue = Integer.MAX_VALUE;
    }

    public BinaryPrimative(byte[] bytes, int seekLocation, boolean littleEndian, Byte[] sentinel) {
        m_bytes = bytes;
        m_seekLocation = seekLocation;
        m_littleEndian = littleEndian;
        m_byteCount = Integer.MAX_VALUE;
        m_sentinel = sentinel;
        m_minByteValue = Integer.MIN_VALUE;
        m_maxByteValue = Integer.MAX_VALUE;
    }

    public BinaryPrimative(byte[] bytes, int seekLocation, boolean littleEndian, int minByteValue, int maxByteValue) {
        m_bytes = bytes;
        m_seekLocation = seekLocation;
        m_littleEndian = littleEndian;
        m_byteCount = Integer.MAX_VALUE;
        m_sentinel = new Byte[] {(byte)'\0'};
        m_minByteValue = minByteValue;
        m_maxByteValue = maxByteValue;
    }

    public ByteBuffer getByteBuffer() {
        LinkedList<Byte> bytes = new LinkedList<Byte>();
        int matchIndex = 0;
        for(int i = m_seekLocation; i < m_bytes.length - 1; i++) {
            if(!(m_minByteValue <= (int)m_bytes[i] && (int)m_bytes[i] <= m_maxByteValue)) {
                break;
            }

            bytes.add(m_bytes[i]);

            if(bytes.size() >= m_byteCount) {
                break;
            }

            if(m_sentinel.length > 0) {
                if (m_sentinel.length - 1 >= matchIndex && m_bytes[i] == m_sentinel[matchIndex]) {
                    matchIndex++;
                } else {
                    matchIndex = 0;
                }

                if (matchIndex == m_sentinel.length) {
                    break;
                }
            }
        }

        // Keep the sentinel out of it by subtracting the match index.
        // This also solves for partial sentinel matches when byte violations happen
        ByteBuffer buffer = ByteBuffer.allocate(bytes.size() - matchIndex);
        buffer.order(m_littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
        for(Byte b : bytes.subList(0, bytes.size() -matchIndex)) {
            buffer.put(b);
        }
        buffer.position(0);
        return buffer;
    }

    public abstract T read();

    public abstract String toString();
}
