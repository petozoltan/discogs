package pet.discogs.data.person;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests are based upon the test data in <tt>data.sql</tt>, which is pre-loaded by the Spring Boot application start.
 */
@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    PersonRepository repository;

    @Test
    void findByName_found() {

        final Optional<Person> persons = repository.findByName("Presser Gábor");

        assertTrue(persons.isPresent());
    }

    @Test
    void findByName_NotFound() {

        final Optional<Person> persons = repository.findByName("Nonexistent Person");

        assertTrue(persons.isEmpty());
    }

    @Test
    void findByNameContainsIgnoreCase_found() {

        final List<Person> persons = repository.findByNameContainsIgnoreCase("pat");

        assertFalse(persons.isEmpty());
        assertEquals(1, persons.size());
    }

    @Test
    void findByNameContainsIgnoreCase_NotFound() {

        final List<Person> persons = repository.findByNameContainsIgnoreCase("nonexistent");

        assertTrue(persons.isEmpty());
    }
}
