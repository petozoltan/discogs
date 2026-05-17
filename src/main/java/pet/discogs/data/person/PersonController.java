package pet.discogs.data.person;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<EntityModel<Person>> newPerson(@RequestBody Person newPerson) {
        return toCreatedResponse(modelAssembler.toModel(repository.save(newPerson)));
    }

    @PutMapping("/persons/{id}")
    ResponseEntity<EntityModel<Person>> replacePerson(@RequestBody Person newPerson, @PathVariable Long id) {
        return toCreatedResponse(modelAssembler.toModel(repository.save(copyAttributes(newPerson, findById(id), true))));
    }

    @PatchMapping("/persons/{id}")
    ResponseEntity<EntityModel<Person>> updatePerson(@RequestBody Person newPerson, @PathVariable Long id) {
        return toCreatedResponse(modelAssembler.toModel(repository.save(copyAttributes(newPerson, findById(id), false))));
    }

    @DeleteMapping("/persons/{id}")
    ResponseEntity<?> deletePerson(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ----------------------------
    // IMPLEMENTATION
    // ----------------------------

    private static Person copyAttributes(final Person newPerson, final Person person, boolean copyNulls) {
        copyAttribute(newPerson, person, Person::getName, Person::setName, copyNulls);
        copyAttribute(newPerson, person, Person::getGender, Person::setGender, copyNulls);
        copyAttribute(newPerson, person, Person::getCountry, Person::setCountry, copyNulls);
        copyAttribute(newPerson, person, Person::getGenre, Person::setGenre, copyNulls);
        copyAttribute(newPerson, person, Person::getInstrument, Person::setInstrument, copyNulls);
        return person;
    }

    private static @NonNull ResponseEntity<EntityModel<Person>> toCreatedResponse(final EntityModel<Person> personModel) {
        return ResponseEntity
                .created(personModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(personModel);
    }

    private @NonNull Person findById(final Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Person.class.getSimpleName(), id));
    }
}
