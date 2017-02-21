package io.github.rlrp;

import java.util.Map;

public class Property<String,V> implements Map.Entry<String,V> {

    private final String m_key;
    private V m_value;

    public Property(String key, V value) {
        m_key = key;
        m_value = value;
    }

    public String getKey() {
        return m_key;
    }

    public V getValue() {
        return m_value;
    }

    public V setValue(V value) {
        V old = m_value;
        m_value = value;
        return old;
    }

    public java.lang.String toString() {
        return getKey() + ":" + getValue().toString() + "\n";
    }
}
