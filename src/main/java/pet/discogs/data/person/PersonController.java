package pet.discogs.data.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pet.discogs.data.common.RestHelper;
import pet.discogs.data.group.Group;
import pet.discogs.data.group.GroupModelAssembler;
import pet.discogs.data.group.GroupRepository;
import pet.discogs.data.recording.Recording;
import pet.discogs.data.recording.RecordingModelAssembler;
import pet.discogs.data.recording.RecordingRepository;

import java.util.Set;

import static pet.discogs.data.common.RestHelper.copyAttribute;

@RestController
//@RequestMapping("/") // TODO What does it mean?
public class PersonController {

    private final PersonRepository personRepository;
    private final PersonModelAssembler personModelAssembler;

    private final GroupRepository groupRepository;
    private final GroupModelAssembler groupModelAssembler;

    private final RecordingRepository recordingRepository;
    private final RecordingModelAssembler recordingModelAssembler;

    @Autowired
    PersonController(
            PersonRepository personRepository,
            PersonModelAssembler personModelAssembler,
            final GroupRepository groupRepository,
            final GroupModelAssembler groupModelAssembler,
            final RecordingRepository recordingRepository,
            final RecordingModelAssembler recordingModelAssembler) {

        this.personRepository = personRepository;
        this.personModelAssembler = personModelAssembler;
        this.groupRepository = groupRepository;
        this.groupModelAssembler = groupModelAssembler;
        this.recordingRepository = recordingRepository;
        this.recordingModelAssembler = recordingModelAssembler;
    }

    // ----------------------------
    // REST API
    // ----------------------------

    @GetMapping("/persons")
    CollectionModel<EntityModel<Person>> getPersons() {
        return personModelAssembler.toCollectionModel(
                personRepository.findAll());
    }

    @PostMapping("/persons")
    ResponseEntity<EntityModel<Person>> newPerson(@RequestBody Person newPerson) {
        return RestHelper.toResponseCreated(
                personModelAssembler.toModel(
                        personRepository.save(newPerson)));
    }

    @GetMapping("/persons/{id}")
    EntityModel<Person> getPerson(@PathVariable Long id) {
        return personModelAssembler.toModel(
                personRepository.getReferenceById(id));
    }

    @PutMapping("/persons/{id}")
    ResponseEntity<EntityModel<Person>> replacePerson(@RequestBody Person newPerson, @PathVariable Long id) {
        return RestHelper.toResponseCreated(
                personModelAssembler.toModel(
                        personRepository.save(
                                copyAttributes(newPerson,
                                        personRepository.getReferenceById(id), true))));
    }

    @PatchMapping("/persons/{id}")
    ResponseEntity<EntityModel<Person>> updatePerson(@RequestBody Person newPerson, @PathVariable Long id) {
        return RestHelper.toResponseCreated(
                personModelAssembler.toModel(
                        personRepository.save(
                                copyAttributes(newPerson,
                                        personRepository.getReferenceById(id), false))));
    }

    @DeleteMapping("/persons/{id}")
    ResponseEntity<?> deletePerson(@PathVariable Long id) {
        personRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/persons/{id}/groups")
    CollectionModel<EntityModel<Group>> getGroups(@PathVariable Long id) {
        final Person person = personRepository.getReferenceById(id);
        final Set<Group> groups = Set.of(
                groupRepository.getReferenceById(1L));
        return groupModelAssembler.toCollectionModel(groups);
    }

    @GetMapping("/persons/{id}/recordings")
    CollectionModel<EntityModel<Recording>> getRecordings(@PathVariable Long id) {
        final Person person = personRepository.getReferenceById(id);
        final Set<Recording> recordings = Set.of(
                recordingRepository.getReferenceById(1L),
                recordingRepository.getReferenceById(2L),
                recordingRepository.getReferenceById(3L));
        return recordingModelAssembler.toCollectionModel(recordings);
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

}
