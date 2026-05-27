package pet.discogs.data.person;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pet.discogs.data.values.Genre.JAZZ;
import static pet.discogs.data.values.Genre.ROCK;
import static pet.discogs.data.values.Instrument.FLUTE;
import static pet.discogs.data.values.Instrument.GUITAR;

/**
 * Tests are based upon the test data in <tt>data.sql</tt>, which is pre-loaded by the Spring Boot application start.
 */
@SpringBootTest
/**
 * In Spring Boot applications prefer {@link org.springframework.transaction.annotation.Transactional}
 * over {@link jakarta.transaction.Transactional} because it offers more options
 * and it is more integrated into Spring Boot.
 */
@Transactional
class PersonRepositoryTest {

    @Autowired
    PersonRepository repository;

    // ======================================
    // Derived Query Methods
    // ======================================

    // --------------------------------------
    // Simple attribute
    // --------------------------------------

    @Test
    void findByName_Found() {
        final Optional<Person> persons = repository.findByName("Presser Gábor");
        assertTrue(persons.isPresent());
    }

    @Test
    void findByName_NotFound() {
        final Optional<Person> persons = repository.findByName("Nonexistent Person");
        assertTrue(persons.isEmpty());
    }

    // --------------------------------------
    // Modifiers
    // --------------------------------------

    @Test
    void findByNameContainsIgnoreCase_Found() {
        final List<Person> persons = repository.findByNameContainsIgnoreCase("pat");
        assertEquals(1, persons.size());
    }

    @Test
    void findByNameContainsIgnoreCase_NotFound() {
        final List<Person> persons = repository.findByNameContainsIgnoreCase("nonexistent");
        assertTrue(persons.isEmpty());
    }

    // --------------------------------------
    // Noun variations
    // --------------------------------------

    @Test
    void findPersonByName_Found() {
        final Optional<Person> persons = repository.findPersonByName("Presser Gábor");
        assertTrue(persons.isPresent());
    }

    @Test
    void findPersonsByName_Found() {
        final Optional<Person> persons = repository.findPersonsByName("Presser Gábor");
        assertTrue(persons.isPresent());
    }

    @Test
    void findAnythingByName_Found() {
        final Optional<Person> persons = repository.findAnythingByName("Presser Gábor");
        assertTrue(persons.isPresent());
    }

    @Test
    void findPersonByNameContains_Found() {
        final List<Person> persons = repository.findPersonByNameContains("Pat");
        assertEquals(1, persons.size());
    }

    @Test
    void findPersonsByNameContains_Found() {
        final List<Person> persons = repository.findPersonsByNameContains("Pat");
        assertEquals(1, persons.size());
    }

    // --------------------------------------
    // Wildcard queries
    // --------------------------------------

    @Test
    void findByNameStartsWith_NoWildcards_Found() {
        final List<Person> persons = repository.findByNameStartsWith("Pat");
        assertEquals(1, persons.size());
    }

    @Test
    void findByNameStartsWith_WithWildcards_NotFound() {
        final List<Person> persons = repository.findByNameStartsWith("Pat%");
        assertTrue(persons.isEmpty());
    }

    @Test
    void findByNameLike_NoWildcards_NotFound() {
        final List<Person> persons = repository.findByNameLike("Pat");
        assertTrue(persons.isEmpty());
    }

    @Test
    void findByNameLike_WithWildcards_Found() {
        final List<Person> persons = repository.findByNameLike("Pat%");
        assertEquals(1, persons.size());
    }

    // --------------------------------------
    // Multiple attributes
    // --------------------------------------

    @Test
    void findByGenreAndInstrument_Found_1() {
        final List<Person> persons = repository.findByGenreAndInstrument(ROCK, GUITAR);
        assertEquals(2, persons.size());
        assertThat(persons)
                .extracting(Person::getName)
                .containsExactlyInAnyOrder("David Gilmour", "Karácsony János");
    }

    @Test
    void findByGenreAndInstrument_Found_2() {
        final List<Person> persons = repository.findByGenreAndInstrument(JAZZ, GUITAR);
        assertEquals(1, persons.size());
        assertThat(persons)
                .extracting(Person::getName)
                .containsExactlyInAnyOrder("Pat Metheny");
    }

    @Test
    void findByGenreAndInstrument_NotFound() {
        final List<Person> persons = repository.findByGenreAndInstrument(JAZZ, FLUTE);
        assertTrue(persons.isEmpty());
    }

    // --------------------------------------
    // Nested properties
    // --------------------------------------

}
