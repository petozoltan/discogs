package pet.discogs.data.person;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import pet.discogs.data.entity.EntityNotFoundException;

import static pet.discogs.data.entity.Entity.copyAttribute;

@RestController
//@RequestMapping("/")
class PersonController {

    private final PersonRepository repository;
    private final PersonModelAssembler modelAssembler;

    @Autowired
    PersonController(PersonRepository repository, PersonModelAssembler modelAssembler) {
        this.repository = repository;
        this.modelAssembler = modelAssembler;
    }

    // ----------------------------
    // REST API
    // ----------------------------

    @GetMapping("/persons")
    CollectionModel<EntityModel<Person>> all() {
        return modelAssembler.toCollectionModel(repository.findAll());
    }

    @GetMapping("/persons/{id}")
    EntityModel<Person> one(@PathVariable Long id) {
        return modelAssembler.toModel(findById(id));
    }

    @PostMapping("/persons")
    EntityModel<Person> newPerson(@RequestBody Person newPerson) {
        return modelAssembler.toModel(repository.save(newPerson));
    }

    @PutMapping("/persons/{id}")
    EntityModel<Person> replacePerson(@RequestBody Person newPerson, @PathVariable Long id) {
        final Person person = findById(id);
        copyPersonAttributes(newPerson, person, true);
        return modelAssembler.toModel(repository.save(person));
    }

    @PatchMapping("/persons/{id}")
    EntityModel<Person> updatePerson(@RequestBody Person newPerson, @PathVariable Long id) {
        final Person person = findById(id);
        copyPersonAttributes(newPerson, person, false);
        return modelAssembler.toModel(repository.save(person));
    }

    @DeleteMapping("/persons/{id}")
    void deletePerson(@PathVariable Long id) {
        repository.deleteById(id);
    }

    // ----------------------------
    // IMPLEMENTATION
    // ----------------------------

    private static void copyPersonAttributes(final Person newPerson, final Person person, boolean copyNulls) {
        copyAttribute(newPerson, person, Person::getName, Person::setName, copyNulls);
        copyAttribute(newPerson, person, Person::getGender, Person::setGender, copyNulls);
        copyAttribute(newPerson, person, Person::getCountry, Person::setCountry, copyNulls);
        copyAttribute(newPerson, person, Person::getGenre, Person::setGenre, copyNulls);
        copyAttribute(newPerson, person, Person::getInstrument, Person::setInstrument, copyNulls);
    }

    private @NonNull Person findById(final Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Person.class.getSimpleName(), id));
    }
}
