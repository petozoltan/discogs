package pet.discogs.data.person;

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
public class PersonModelAssembler implements RepresentationModelAssembler<Person, EntityModel<Person>> {

    @Override
    public EntityModel<Person> toModel(final Person person) {

        final EntityModel<Person> personModel = EntityModel.of(person,
                linkTo(methodOn(PersonController.class).getPerson(person.getId())).withSelfRel(),
                linkTo(methodOn(PersonController.class).getPersons()).withRel("persons"));

        // They can be conditional, like 'if hasGroups()'.
        personModel.add(linkTo(methodOn(PersonController.class).getGroups(person.getId())).withRel("groups"));
        personModel.add(linkTo(methodOn(PersonController.class).getRecordings(person.getId())).withRel("recordings"));

        return personModel;
    }

    @Override
    public CollectionModel<EntityModel<Person>> toCollectionModel(final Iterable<? extends Person> entities) {

        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(collectingAndThen(Collectors.toList(), personModels ->
                        CollectionModel.of(personModels, linkTo(methodOn(PersonController.class).getPersons()).withSelfRel())));
    }
}
