package io.github.rlrp;

import java.util.ArrayList;
import java.util.List;

public class PropertyFactory {

    private PropertyFactory() {}

    public static Property getNextProperty(ReplayStream stream) {
        String name = stream.readString();

        if (name.equals("None")) {
            return null;
        }

        String type = stream.readString();
        int dataLength = stream.readInteger32(true);
        int unknown = stream.readInteger32(true);
        Object value = null;

        try {
            value = getPropertyValue(stream, type, dataLength);
        }
        catch (Exception e) {
            System.out.println("Caught exception, " + e);
            System.exit(1);
        }

        return new Property<String,Object>(name, value);
    }

    private static Object getPropertyValue(ReplayStream stream, String type, int length) throws Exception {
        if (type.equals("IntProperty")) {
            return stream.readInteger32(true);
        }
        else if (type.equals("StrProperty")) {
            return stream.readString(length);
        }
        else if (type.equals("ArrayProperty")) {
            List<List<Property>> nestedPropertyList = new ArrayList<List<Property>>();
            int arrayLen = stream.readInteger32(true);

            for (int i = 0; i < arrayLen; i++) {
                List<Property> currPropertyList = new ArrayList<Property>();
                Property property;

                do {
                    property = PropertyFactory.getNextProperty(stream);
                    if (property != null) {
                        currPropertyList.add(property);
                    }
                } while (property != null);

                nestedPropertyList.add(currPropertyList);
            }

            return nestedPropertyList;
        }
        else {
            throw new Exception("Invalid type: " + type);
        }
    }
}


