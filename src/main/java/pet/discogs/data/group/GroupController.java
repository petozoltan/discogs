package pet.discogs.data.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pet.discogs.data.common.RestHelper;
import pet.discogs.data.person.Person;
import pet.discogs.data.person.PersonModelAssembler;
import pet.discogs.data.person.PersonRepository;
import pet.discogs.data.recording.Recording;
import pet.discogs.data.recording.RecordingModelAssembler;
import pet.discogs.data.recording.RecordingRepository;

import java.util.Set;

import static pet.discogs.data.common.RestHelper.copyAttribute;

@RestController
public class GroupController {

    private final PersonRepository personRepository;
    private final PersonModelAssembler personModelAssembler;

    private final GroupRepository groupRepository;
    private final GroupModelAssembler groupModelAssembler;

    private final RecordingRepository recordingRepository;
    private final RecordingModelAssembler recordingModelAssembler;

    @Autowired
    GroupController(
            final PersonRepository personRepository,
            final PersonModelAssembler personModelAssembler,
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

    @GetMapping("/groups")
    CollectionModel<EntityModel<Group>> getGroups() {
        return groupModelAssembler.toCollectionModel(groupRepository.findAll());
    }

    @PostMapping("/groups")
    ResponseEntity<EntityModel<Group>> newGroup(@RequestBody Group newGroup) {
        return RestHelper.toResponseCreated(
                groupModelAssembler.toModel(
                        groupRepository.save(newGroup)));
    }

    @GetMapping("/groups/{id}")
    EntityModel<Group> getGroup(@PathVariable Long id) {
        return groupModelAssembler.toModel(groupRepository.getReferenceById(id));
    }

    @PutMapping("/groups/{id}")
    ResponseEntity<EntityModel<Group>> replaceGroup(@RequestBody Group newGroup, @PathVariable Long id) {
        return RestHelper.toResponseCreated(
                groupModelAssembler.toModel(
                        groupRepository.save(
                                copyAttributes(newGroup,
                                        groupRepository.getReferenceById(id), true))));
    }

    @PatchMapping("/groups/{id}")
    ResponseEntity<EntityModel<Group>> updateGroup(@RequestBody Group newGroup, @PathVariable Long id) {
        return RestHelper.toResponseCreated(
                groupModelAssembler.toModel(
                        groupRepository.save(
                                copyAttributes(newGroup,
                                        groupRepository.getReferenceById(id), false))));
    }

    @DeleteMapping("/groups/{id}")
    ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        groupRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/groups/{id}/persons")
    CollectionModel<EntityModel<Person>> getPersons(@PathVariable Long id) {
        final Group group = groupRepository.getReferenceById(id);
        final Set<Person> persons = Set.of(
                personRepository.getReferenceById(1L),
                personRepository.getReferenceById(2L),
                personRepository.getReferenceById(3L));
        return personModelAssembler.toCollectionModel(persons);
    }

    @GetMapping("/groups/{id}/recordings")
    CollectionModel<EntityModel<Recording>> getRecordings(@PathVariable Long id) {
        final Group group = groupRepository.getReferenceById(id);
        final Set<Recording> recordings = Set.of(
                recordingRepository.getReferenceById(4L),
                recordingRepository.getReferenceById(5L),
                recordingRepository.getReferenceById(6L));
        return recordingModelAssembler.toCollectionModel(recordings);
    }

    // ----------------------------
    // IMPLEMENTATION
    // ----------------------------

    private static Group copyAttributes(final Group newGroup, final Group group, boolean copyNulls) {
        copyAttribute(newGroup, group, Group::getName, Group::setName, copyNulls);
        return group;
    }
}
