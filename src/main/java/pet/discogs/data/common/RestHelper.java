package pet.discogs.data.common;

import org.jspecify.annotations.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class RestHelper {

    public static @NonNull <T> ResponseEntity<EntityModel<T>> toResponseCreated(final EntityModel<T> personModel) {
        return ResponseEntity
                .created(personModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(personModel);
    }

    /**
     * It helps to implement the PUT and PATCH commands without code repetition.
     */
    public static <T, V> void copyAttribute(
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
}
