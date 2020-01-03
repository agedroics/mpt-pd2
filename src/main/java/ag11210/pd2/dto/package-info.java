@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(value = BooleanAdapter.class, type = Boolean.class),
        @XmlJavaTypeAdapter(value = DurationAdapter.class, type = Duration.class),
        @XmlJavaTypeAdapter(value = LocalDateAdapter.class, type = LocalDate.class)
})
package ag11210.pd2.dto;

import ag11210.pd2.configuration.BooleanAdapter;
import ag11210.pd2.configuration.DurationAdapter;
import ag11210.pd2.configuration.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.Duration;
import java.time.LocalDate;