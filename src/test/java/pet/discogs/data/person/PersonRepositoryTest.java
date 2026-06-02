package pet.discogs.data.person;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pet.discogs.data.group.Group;
import pet.discogs.data.group.GroupRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static pet.discogs.data.values.Country.UK;
import static pet.discogs.data.values.Gender.MALE;
import static pet.discogs.data.values.Genre.JAZZ;
import static pet.discogs.data.values.Genre.ROCK;
import static pet.discogs.data.values.Instrument.*;

/// Tests are based upon the test data in [pet.discogs.data.MockData], pre-loaded by the Spring Boot application start.
@SpringBootTest
@Transactional
class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    GroupRepository groupRepository;

    // ======================================
    // Derived Query Methods
    // ======================================

    // --------------------------------------
    // Simple attribute
    // --------------------------------------

    @Test
    void findByName_Found() {
        personRepository.saveAndFlush(new Person("Joe", MALE, UK, GUITAR, ROCK));
        assumeTrue(personRepository.findAll().size() == 1);
        final Optional<Person> person = personRepository.findByName("Joe");
        assertTrue(person.isPresent());
    }

    @Test
    void findByName_NotFound() {
        personRepository.save(new Person("Joe", MALE, UK, GUITAR, ROCK));
        assumeTrue(personRepository.findAll().size() == 1);
        final Optional<Person> person = personRepository.findByName("joe");
        assertTrue(person.isEmpty());
    }

    // --------------------------------------
    // Modifiers
    // --------------------------------------

    @Test
    void findByNameContainsIgnoreCase_Found() {
        personRepository.save(new Person("Joe", MALE, UK, GUITAR, ROCK));
        assumeTrue(personRepository.findAll().size() == 1);
        final List<Person> persons = personRepository.findByNameContainsIgnoreCase("joe");
        assertEquals(1, persons.size());
    }

    @Test
    void findByNameContainsIgnoreCase_NotFound() {
        personRepository.save(new Person("Joe", MALE, UK, GUITAR, ROCK));
        assumeTrue(personRepository.findAll().size() == 1);
        final List<Person> persons = personRepository.findByNameContainsIgnoreCase("jack");
        assertTrue(persons.isEmpty());
    }

    // --------------------------------------
    // Noun variations
    // --------------------------------------

    @Test
    void findPersonByName_WrongCase_NotFound() {
        personRepository.save(new Person("Joe", MALE, UK, GUITAR, ROCK));
        assumeTrue(personRepository.findAll().size() == 1);
        final Optional<Person> person = personRepository.findPersonByName("joe");
        assertTrue(person.isEmpty());
    }

    @Test
    void findPersonByName_WrongName_NotFound() {
        personRepository.save(new Person("Joe", MALE, UK, GUITAR, ROCK));
        assumeTrue(personRepository.findAll().size() == 1);
        final Optional<Person> person = personRepository.findPersonByName("Jack");
        assertTrue(person.isEmpty());
    }

    @Test
    void findPersonByName_Found() {
        personRepository.save(new Person("Joe", MALE, UK, GUITAR, ROCK));
        assumeTrue(personRepository.findAll().size() == 1);
        final Optional<Person> person = personRepository.findPersonByName("Joe");
        assertTrue(person.isPresent());
    }

    @Test
    void findPersonsByName_Found() {
        personRepository.save(new Person("Joe", MALE, UK, GUITAR, ROCK));
        assumeTrue(personRepository.findAll().size() == 1);
        final Optional<Person> person = personRepository.findPersonsByName("Joe");
        assertTrue(person.isPresent());
    }

    @Test
    void findAnythingByName_Found() {
        personRepository.save(new Person("Joe", MALE, UK, GUITAR, ROCK));
        assumeTrue(personRepository.findAll().size() == 1);
        final Optional<Person> person = personRepository.findAnythingByName("Joe");
        assertTrue(person.isPresent());
    }

    @Test
    void findPersonByNameContains_Found() {
        personRepository.save(new Person("Joe Smith", MALE, UK, GUITAR, ROCK));
        assumeTrue(personRepository.findAll().size() == 1);
        final List<Person> persons = personRepository.findPersonByNameContains("Joe");
        assertEquals(1, persons.size());
    }

    @Test
    void findPersonsByNameContains_Found() {
        personRepository.save(new Person("Joe Smith", MALE, UK, GUITAR, ROCK));
        assumeTrue(personRepository.findAll().size() == 1);
        final List<Person> persons = personRepository.findPersonsByNameContains("Joe");
        assertEquals(1, persons.size());
    }

    // --------------------------------------
    // Wildcard queries
    // --------------------------------------

    @Test
    void findByNameStartsWith_NoWildcards_Found() {
        personRepository.save(new Person("Joe Smith", MALE, UK, GUITAR, ROCK));
        assumeTrue(personRepository.findAll().size() == 1);
        final List<Person> persons = personRepository.findByNameStartsWith("Joe");
        assertEquals(1, persons.size());
    }

    @Test
    void findByNameStartsWith_WithWildcards_NotFound() {
        personRepository.save(new Person("Joe Smith", MALE, UK, GUITAR, ROCK));
        assumeTrue(personRepository.findAll().size() == 1);
        final List<Person> persons = personRepository.findByNameStartsWith("Joe%");
        assertTrue(persons.isEmpty());
    }

    @Test
    void findByNameLike_NoWildcards_NotFound() {
        personRepository.save(new Person("Joe Smith", MALE, UK, GUITAR, ROCK));
        assumeTrue(personRepository.findAll().size() == 1);
        final List<Person> persons = personRepository.findByNameLike("Joe");
        assertTrue(persons.isEmpty());
    }

    @Test
    void findByNameLike_WithWildcards_Found() {
        personRepository.save(new Person("Joe Smith", MALE, UK, GUITAR, ROCK));
        assumeTrue(personRepository.findAll().size() == 1);
        final List<Person> persons = personRepository.findByNameLike("Joe%");
        assertEquals(1, persons.size());
    }

    // --------------------------------------
    // Multiple attributes
    // --------------------------------------

    @Test
    void findByGenreAndInstrument_Found() {
        personRepository.save(new Person("Joe", MALE, UK, GUITAR, ROCK));
        assumeTrue(personRepository.findAll().size() == 1);
        final List<Person> persons = personRepository.findByGenreAndInstrument(ROCK, GUITAR);
        assertEquals(1, persons.size());
        assertThat(persons).extracting(Person::getName).containsExactly("Joe");
    }

    @Test
    void findByGenreAndInstrument_NotFound() {
        personRepository.save(new Person("Joe", MALE, UK, GUITAR, ROCK));
        assumeTrue(personRepository.findAll().size() == 1);
        final List<Person> persons = personRepository.findByGenreAndInstrument(JAZZ, FLUTE);
        assertTrue(persons.isEmpty());
    }

    // --------------------------------------
    // Nested properties
    // --------------------------------------

    @Test
    void findByGroupsName_Found() {

        final Person person = new Person("Joe", MALE, UK, GUITAR, ROCK);
        final Group group = new Group("Group");
        group.addMember(person);
        groupRepository.save(group);

        assumeTrue(personRepository.findAll().size() == 1);
        assumeTrue(groupRepository.findAll().size() == 1);

        final List<Person> persons = personRepository.findByGroupsName("Group");

        assertEquals(1, persons.size());
        assertThat(persons).extracting(Person::getName).containsExactly("Joe");
    }

    @Test
    void findByGroupsName_NotFound() {

        final Person person = new Person("Joe", MALE, UK, GUITAR, ROCK);
        final Group group = new Group("Group");
        group.addMember(person);
        groupRepository.save(group);

        assumeTrue(personRepository.findAll().size() == 1);
        assumeTrue(groupRepository.findAll().size() == 1);

        final List<Person> persons = personRepository.findByGroupsName("Band");

        assertTrue(persons.isEmpty());
    }

    // --------------------------------------
    // Distinct
    // --------------------------------------

    /// I could not create a non-distinct case
    @Test
    void findByGroupsName_NotDistinct() {

        final Group group1 = new Group("Group");
        final Person person = new Person("Joe", MALE, UK, GUITAR, ROCK);
        group1.addMember(person);
        groupRepository.save(group1);

        final Group group2 = new Group("Band");
        final Optional<Person> samePerson = personRepository.findByName("Joe");
        assumeTrue(samePerson.isPresent());
        group2.addMember(samePerson.get());
        groupRepository.save(group2);

        assumeTrue(personRepository.findAll().size() == 1);
        assumeTrue(groupRepository.findAll().size() == 2);

        final List<Person> persons = personRepository.findByGroupsName("Group");

        assertEquals(1, persons.size());
        assertThat(persons).extracting(Person::getName).containsExactly("Joe");
    }

    @Test
    void findDistinctByGroupsName_Distinct() {

        final Group group1 = new Group("Group");
        final Person person = new Person("Joe", MALE, UK, GUITAR, ROCK);
        group1.addMember(person);
        groupRepository.save(group1);

        final Group group2 = new Group("Band");
        final Optional<Person> samePerson = personRepository.findByName("Joe");
        assumeTrue(samePerson.isPresent());
        group2.addMember(samePerson.get());
        groupRepository.save(group2);

        assumeTrue(personRepository.findAll().size() == 1);
        assumeTrue(groupRepository.findAll().size() == 2);

        final List<Person> persons = personRepository.findDistinctByGroupsName("Group");

        assertEquals(1, persons.size());
        assertThat(persons).extracting(Person::getName).containsExactly("Joe");
    }

    // --------------------------------------
    // Limits & Sorting
    // --------------------------------------

    @Test
    void findFirst2ByCountryOrderByNameDesc() {

        personRepository.saveAll(List.of(
                new Person("Bill", MALE, UK, BASS, ROCK),
                new Person("Gary", MALE, UK, DRUMS, ROCK),
                new Person("Jack", MALE, UK, VOCAL, ROCK),
                new Person("Joe", MALE, UK, GUITAR, ROCK)));

        assumeTrue(personRepository.findAll().size() == 4);

        final List<Person> persons = personRepository.findFirst2ByCountryOrderByNameDesc(UK);

        assertEquals(2, persons.size());
        assertThat(persons).extracting(Person::getName).containsExactlyInAnyOrder("Joe", "Jack");
    }
}
