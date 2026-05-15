package pet.discogs.data.entity;

import java.util.function.BiConsumer;
import java.util.function.Function;

public interface Entity {

    /**
     * It helps to implement the PUT and PATCH commands without code repetition.
     */
    static <T, V> void copyAttribute(
            final T source,
            final T target,
            final Function<T, V> getter,
            final BiConsumer<T, V> setter,
            final boolean copyNulls) {

        final V value = getter.apply(source);
        if (value != null || copyNulls) {
            setter.accept(target, value);
        }
    }

    Long getId();
}
