package pet.discogs.data.recording;

import org.jspecify.annotations.NonNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.collectingAndThen;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@ControllerAdvice
public class RecordingModelAssembler implements RepresentationModelAssembler<Recording, EntityModel<Recording>> {

    @Override
    public EntityModel<Recording> toModel(final Recording recording) {

        final EntityModel<Recording> recordingModel = EntityModel.of(recording,
                linkTo(methodOn(RecordingController.class).getRecording(recording.getId())).withSelfRel(),
                linkTo(methodOn(RecordingController.class).getRecordings()).withRel("recordings"));

        // They can be conditional, like 'if hasPersons()'.
        recordingModel.add(linkTo(methodOn(RecordingController.class).getPersons(recording.getId())).withRel("persons"));
        recordingModel.add(linkTo(methodOn(RecordingController.class).getGroup(recording.getId())).withRel("group"));

        return recordingModel;
    }

    @Override
    public @NonNull CollectionModel<EntityModel<Recording>> toCollectionModel(final Iterable<? extends Recording> entities) {

        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(collectingAndThen(Collectors.toList(), recordingModels ->
                        CollectionModel.of(recordingModels, linkTo(methodOn(RecordingController.class).getRecordings()).withSelfRel())));
    }
}
