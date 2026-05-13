package pet.discogs.data.person;

import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import pet.discogs.data.entity.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
//@RequestMapping("/")
class PersonController {

    private final PersonRepository repository;

    PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    // ----------------------------
    // REST API
    // ----------------------------

    @GetMapping("/persons")
    CollectionModel<EntityModel<Person>> all() {
        final List<EntityModel<Person>> persons = repository.findAll().stream()
                .map(PersonController::addLinks)
                .collect(toList());
        return addLinks(persons);
    }

    @GetMapping("/persons/{id}")
    EntityModel<Person> one(@PathVariable Long id) {
        return addLinks(findById(id));
    }

    @PostMapping("/persons")
    EntityModel<Person> newPerson(@RequestBody Person newPerson) {
        return addLinks(repository.save(newPerson));
    }

    @PutMapping("/persons/{id}")
    EntityModel<Person> replacePerson(@RequestBody Person newPerson, @PathVariable Long id) {

        final Person person = findById(id);

        person.setName(newPerson.getName());
        person.setGender(newPerson.getGender());
        person.setCountry(newPerson.getCountry());
        person.setGenre(newPerson.getGenre());
        person.setInstrument(newPerson.getInstrument());

        return addLinks(repository.save(person));
    }

    @PatchMapping("/persons/{id}")
    EntityModel<Person> updatePerson(@RequestBody Person newPerson, @PathVariable Long id) {

        final Person person = findById(id);

        Optional.of(newPerson).map(Person::getName).ifPresent(person::setName);
        Optional.of(newPerson).map(Person::getGender).ifPresent(person::setGender);
        Optional.of(newPerson).map(Person::getCountry).ifPresent(person::setCountry);
        Optional.of(newPerson).map(Person::getGenre).ifPresent(person::setGenre);
        Optional.of(newPerson).map(Person::getInstrument).ifPresent(person::setInstrument);

        return addLinks(repository.save(person));
    }

    @DeleteMapping("/persons/{id}")
    void deletePerson(@PathVariable Long id) {
        repository.deleteById(id);
    }

    // ----------------------------
    // IMPLEMENTATION
    // ----------------------------

    @org.jetbrains.annotations.Contract("_ -> new")
    private static @NonNull EntityModel<Person> addLinks(final Person person) {
        return EntityModel.of(person,
                linkTo(methodOn(PersonController.class).one(person.getId())).withSelfRel(),
                linkTo(methodOn(PersonController.class).all()).withRel("persons"));
    }

    @Contract("_ -> new")
    private static @NonNull CollectionModel<EntityModel<Person>> addLinks(final List<EntityModel<Person>> persons) {
        return CollectionModel.of(persons,
                linkTo(methodOn(PersonController.class).all()).withSelfRel());
    }

    private @NonNull Person findById(final Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Person.class.getSimpleName(), id));
    }
}
