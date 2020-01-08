package ag11210.pd2;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.Objects;

@Getter
@Setter
public class TimeInterval {

    private final Duration from;
    private final Duration to;

    public TimeInterval(Duration from, Duration to) {
        this.from = Objects.requireNonNull(from);
        if (Objects.requireNonNull(to).compareTo(from) < 0) {
            throw new IllegalArgumentException("Invalid interval");
        }
        this.to = to;
    }

    public boolean includes(Duration time) {
        return from.compareTo(time) <= 0 && to.compareTo(time) > 0;
    }

    public Duration getLength() {
        return to.minus(from);
    }
}
