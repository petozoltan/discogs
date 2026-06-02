package pet.discogs.data.person;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.discogs.data.values.Country;
import pet.discogs.data.values.Genre;
import pet.discogs.data.values.Instrument;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    // ======================================
    // Derived Query Methods
    // ======================================

    // --------------------------------------
    // Simple attribute
    // --------------------------------------

    Optional<Person> findByName(String name);

    // --------------------------------------
    // Modifiers
    // --------------------------------------

    List<Person> findByNameContainsIgnoreCase(String name);

    // --------------------------------------
    // Noun variations
    // --------------------------------------

    Optional<Person> findPersonByName(String name);

    Optional<Person> findPersonsByName(String name);

    Optional<Person> findAnythingByName(String name);

    List<Person> findPersonByNameContains(String name);

    List<Person> findPersonsByNameContains(String name);

    // --------------------------------------
    // Wildcard queries
    // --------------------------------------

    /**
     * Contains, Starts, Ends: Wildards must not be given.
     */
    List<Person> findByNameStartsWith(String name);

    /**
     * Like: Wildards must be given.
     */
    List<Person> findByNameLike(String name);

    // --------------------------------------
    // Multiple attributes
    // --------------------------------------

    List<Person> findByGenreAndInstrument(Genre genre, Instrument instrument);

    // --------------------------------------
    // Nested properties
    // --------------------------------------

    List<Person> findByGroupsName(String groupsName);

    // --------------------------------------
    // Distinct
    // --------------------------------------

    List<Person> findDistinctByGroupsName(String groupsName);

    // --------------------------------------
    // Limits & Sorting
    // --------------------------------------

    List<Person> findFirst2ByCountryOrderByNameDesc(Country country);
}