package pet.discogs.data.person;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Find the naming convention for the possible query methods here:
 * <a href="https://docs.spring.io/spring-data/rest/reference/data-commons/repositories/query-keywords-reference.html">Repository query keywords</a>
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByName(String name);

    List<Person> findByNameContainsIgnoreCase(String name);
}
