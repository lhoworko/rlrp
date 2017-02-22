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
            value = getPropertyValue(name, stream, PropertyType.valueOf(type), dataLength);
        }
        catch (Exception e) {
            System.out.println("Caught exception, " + e);
            System.exit(1);
        }

        return new Property<String,Object>(name, value);
    }

    private static Object getPropertyValue(String name, ReplayStream stream, PropertyType propertyType, int length) throws Exception {
        switch (propertyType) {
            case IntProperty:
                return stream.readInteger32(true);
            case NameProperty:
            case StrProperty:
                if(name.equals("PlayerName")){
                    return stream.readString();
                }
                // This works for some of the older replays
                // Dies in a fire for replay 5F2FDD7C4557AA31982BE79BAA63373F
                if(name.equals("PlayerName") ||
                        name.equals("Name") ||
                        name.equals("MapName") ||
                        name.equals("MatchType") ||
                        name.equals("Date") ||
                        name.equals("BuildVersion") ||
                        name.equals("ReplayName")) {
                    return stream.readString();
                }
                return stream.readString(length);
            case ArrayProperty:
                return getArrayProperty(stream, length);
            case ByteProperty:
                return stream.readEnum(length);
            case QWordProperty:
                return stream.readLong();
            case BoolProperty:
                return stream.readBoolean();
            case FloatProperty:
                return stream.readFloat();
            default:
                throw new Exception("Invalid type: " + propertyType);
        }
    }

    private static Object getArrayProperty(ReplayStream stream, int length) {
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

}


