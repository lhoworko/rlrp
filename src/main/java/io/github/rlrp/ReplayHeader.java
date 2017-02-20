package io.github.rlrp;

import java.util.ArrayList;
import java.util.List;

public class ReplayHeader {

    private Integer length;
    private Integer crc;
    private Integer majorVersion;
    private Integer minorVersion;
    private List<Property> propertyList;

    private ReplayHeader() {}

    public static ReplayHeader deserialize(ReplayStream stream) {
        ReplayHeader header = new ReplayHeader();

        header.length = stream.readInteger32(true);
        header.crc = stream.readInteger32(true);
        header.majorVersion = stream.readInteger32(true);
        header.minorVersion = stream.readInteger32(true);

        // TAGame.Replay_Soccer_TA signifies start of header data.
        String s = stream.readString();

        header.propertyList = new ArrayList<Property>();

        Property property;
        do {
            property = PropertyFactory.getNextProperty(stream);
            header.propertyList.add(property);
            System.out.println(property);
        } while (property != null);

        return header;
    }
}
