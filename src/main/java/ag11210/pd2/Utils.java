package ag11210.pd2;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Utils {

    public static <T, K> Collector<T, ?, Map<K, T>> groupingById(Function<T, K> keyExtractor) {
        return Collectors.groupingBy(keyExtractor, Collectors.reducing(null, (a, b) -> a == null ? b : a));
    }
}
