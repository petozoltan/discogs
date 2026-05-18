package pet.discogs.data.group;

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
public class GroupModelAssembler implements RepresentationModelAssembler<Group, EntityModel<Group>> {

    @Override
    public EntityModel<Group> toModel(final Group group) {

        final EntityModel<Group> groupModel = EntityModel.of(group,
                linkTo(methodOn(GroupController.class).getGroup(group.getId())).withSelfRel(),
                linkTo(methodOn(GroupController.class).getGroups()).withRel("groups"));

        // TODO They can be conditional, like 'if hasRecordings()'.
        groupModel.add(linkTo(methodOn(GroupController.class).getPersons(group.getId())).withRel("persons"));
        groupModel.add(linkTo(methodOn(GroupController.class).getRecordings(group.getId())).withRel("recordings"));

        return groupModel;
    }

    @Override
    public CollectionModel<EntityModel<Group>> toCollectionModel(final Iterable<? extends Group> entities) {

        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(collectingAndThen(Collectors.toList(), groupModels ->
                        CollectionModel.of(groupModels, linkTo(methodOn(GroupController.class).getGroups()).withSelfRel())));
    }
}
