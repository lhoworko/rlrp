package io.github.rlrp;

public class EnumProperty {
    private String m_type;
    private String m_value;

    public String getType() {
        return m_type;
    }

    public String getValue() {
        return m_value;
    }

    public EnumProperty(String type, String value) {
        m_type = type;
        m_value = value;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", m_type, m_value);
    }
}
