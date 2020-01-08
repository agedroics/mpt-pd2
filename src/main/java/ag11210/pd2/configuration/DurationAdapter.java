package ag11210.pd2.configuration;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.Duration;

public class DurationAdapter extends XmlAdapter<String, Duration> {

    @Override
    public Duration unmarshal(String v) {
        if (v == null) {
            return null;
        }
        String[] parts = v.split(":");
        return Duration.ofSeconds(Long.parseLong(parts[0]) * 60 + Long.parseLong(parts[1]));
    }

    @Override
    public String marshal(Duration v) {
        return v == null ? null : String.format("%02d:%02d", v.toMinutes(), v.toSecondsPart());
    }
}
