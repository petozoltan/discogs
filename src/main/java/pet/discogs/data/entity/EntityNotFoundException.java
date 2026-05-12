package pet.discogs.data.entity;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityClass, Long id) {
        super("Could not find " + entityClass + " " + id);
    }
}
