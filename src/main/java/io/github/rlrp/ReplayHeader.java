package io.github.rlrp;

import java.io.ByteArrayInputStream;

public class ReplayHeader {

    private Integer length;
    private Integer crc;
    private Integer majorVersion;
    private Integer minorVersion;

    private ReplayHeader() {}

    public static ReplayHeader deserialize(byte[] data) {
        ReplayHeader header = new ReplayHeader();
        ByteArrayInputStream stream = new ByteArrayInputStream(data);

        header.length = BinaryInteger.read32(stream, true);
        header.crc = BinaryInteger.read32(stream, true);
        header.majorVersion = BinaryInteger.read32(stream, true);
        header.minorVersion = BinaryInteger.read32(stream, true);

        // TAGame.Replay_Soccer_TA signifies start of header data.
        String s = BinaryString.read(stream);

        return header;
    }
}
