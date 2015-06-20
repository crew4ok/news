package urujdas.util;

import java.util.Optional;
import java.util.function.Function;

public class MapperUtils {

    public static <U, T> T fromNullable(U o, Function<U, T> mapperFunction) {
        return Optional.ofNullable(o)
                .map(mapperFunction)
                .orElse(null);
    }

}
