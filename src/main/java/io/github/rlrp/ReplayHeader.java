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
            if(property != null) {
                header.propertyList.add(property);
            }
        } while (property != null);

        return header;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Length: %d\n", this.length));
        builder.append(String.format("crc: %d\n", this.crc));
        builder.append(String.format("majorVersion: %d\n", this.majorVersion));
        builder.append(String.format("minorVersion: %d\n", this.minorVersion));

        for(Object p : propertyList) {
            builder.append("\t" + p.toString() + "\n");
        }
        return builder.toString();
    }
}
