package ag11210.pd2.configuration;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalQueries;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @Override
    public LocalDate unmarshal(String v) {
        return v == null ? null : formatter.parse(v, TemporalQueries.localDate());
    }

    @Override
    public String marshal(LocalDate v) {
        return v == null ? null : formatter.format(v);
    }
}
