package pet.discogs.data.person;

import org.jspecify.annotations.NonNull;
import org.springframework.web.bind.annotation.*;
import pet.discogs.data.entity.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping("/")
class PersonController {

    private final PersonRepository repository;

    PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/persons")
    List<Person> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/persons")
    Person newPerson(@RequestBody Person newPerson) {
        return repository.save(newPerson);
    }

    // Single item

    @GetMapping("/persons/{id}")
    Person one(@PathVariable Long id) {
        return findById(id);
    }

    private @NonNull Person findById(final Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Person.class.getSimpleName(), id));
    }

    @PutMapping("/persons/{id}")
    Person replacePerson(@RequestBody Person newPerson, @PathVariable Long id) {

        final Person person = findById(id);

        Optional.of(newPerson).map(Person::getName).ifPresent(person::setName);
        Optional.of(newPerson).map(Person::getGender).ifPresent(person::setGender);
        Optional.of(newPerson).map(Person::getCountry).ifPresent(person::setCountry);
        Optional.of(newPerson).map(Person::getGenre).ifPresent(person::setGenre);
        Optional.of(newPerson).map(Person::getInstrument).ifPresent(person::setInstrument);

        return repository.save(person);
    }

    @DeleteMapping("/persons/{id}")
    void deletePerson(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
