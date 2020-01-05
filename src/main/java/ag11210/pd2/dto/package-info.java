@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(value = DurationAdapter.class, type = Duration.class)
package ag11210.pd2.dto;

import ag11210.pd2.configuration.DurationAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.Duration;