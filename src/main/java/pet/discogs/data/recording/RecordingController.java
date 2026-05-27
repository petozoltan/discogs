package pet.discogs.data.recording;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pet.discogs.data.common.RestHelper;
import pet.discogs.data.group.Group;
import pet.discogs.data.group.GroupModelAssembler;
import pet.discogs.data.group.GroupRepository;
import pet.discogs.data.person.Person;
import pet.discogs.data.person.PersonModelAssembler;
import pet.discogs.data.person.PersonRepository;

import java.util.Set;

import static pet.discogs.data.common.RestHelper.copyAttribute;

@RestController
public class RecordingController {

    private final PersonRepository personRepository;
    private final PersonModelAssembler personModelAssembler;

    private final GroupRepository groupRepository;
    private final GroupModelAssembler groupModelAssembler;

    private final RecordingRepository recordingRepository;
    private final RecordingModelAssembler recordingModelAssembler;

    @Autowired
    RecordingController(
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

    @GetMapping("/recordings")
    CollectionModel<EntityModel<Recording>> getRecordings() {
        return recordingModelAssembler.toCollectionModel(recordingRepository.findAll());
    }

    @PostMapping("/recordings")
    ResponseEntity<EntityModel<Recording>> newRecording(@RequestBody Recording newRecording) {
        return RestHelper.toResponseCreated(
                recordingModelAssembler.toModel(
                        recordingRepository.save(newRecording)));
    }

    @GetMapping("/recordings/{id}")
    EntityModel<Recording> getRecording(@PathVariable Long id) {
        return recordingModelAssembler.toModel(recordingRepository.getReferenceById(id));
    }

    @PutMapping("/recordings/{id}")
    ResponseEntity<EntityModel<Recording>> replaceRecording(@RequestBody Recording newRecording, @PathVariable Long id) {
        return RestHelper.toResponseCreated(
                recordingModelAssembler.toModel(
                        recordingRepository.save(
                                copyAttributes(newRecording,
                                        recordingRepository.getReferenceById(id), true))));
    }

    @PatchMapping("/recordings/{id}")
    ResponseEntity<EntityModel<Recording>> updateRecording(@RequestBody Recording newRecording, @PathVariable Long id) {
        return RestHelper.toResponseCreated(
                recordingModelAssembler.toModel(
                        recordingRepository.save(
                                copyAttributes(newRecording,
                                        recordingRepository.getReferenceById(id), false))));
    }

    @DeleteMapping("/recordings/{id}")
    ResponseEntity<?> deleteRecording(@PathVariable Long id) {
        recordingRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recordings/{id}/group")
    EntityModel<Group> getGroup(@PathVariable Long id) {
        final Recording recording = recordingRepository.getReferenceById(id);
        final Group group = groupRepository.getReferenceById(1L);
        return groupModelAssembler.toModel(group);
    }

    @GetMapping("/recordings/{id}/persons")
    CollectionModel<EntityModel<Person>> getPersons(@PathVariable Long id) {
        final Recording recording = recordingRepository.getReferenceById(id);
        final Set<Person> persons = Set.of(
                personRepository.getReferenceById(4L),
                personRepository.getReferenceById(5L),
                personRepository.getReferenceById(6L));
        return personModelAssembler.toCollectionModel(persons);
    }

    // ----------------------------
    // IMPLEMENTATION
    // ----------------------------

    private static Recording copyAttributes(final Recording newRecording, final Recording recording, boolean copyNulls) {
        copyAttribute(newRecording, recording, Recording::getTitle, Recording::setTitle, copyNulls);
        copyAttribute(newRecording, recording, Recording::getYear, Recording::setYear, copyNulls);
        copyAttribute(newRecording, recording, Recording::getType, Recording::setType, copyNulls);
        return recording;
    }
}
