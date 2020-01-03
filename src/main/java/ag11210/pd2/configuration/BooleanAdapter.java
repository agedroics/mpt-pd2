package ag11210.pd2.configuration;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BooleanAdapter extends XmlAdapter<String, Boolean> {

    @Override
    public Boolean unmarshal(String v) {
        return "J".equals(v);
    }

    @Override
    public String marshal(Boolean v) {
        return v == null ? null : (v ? "J" : "N");
    }
}
