package pet.discogs.data;

import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.joining;

//@Entity
public class Group {

    //    @Id
//    @GeneratedValue
    private Long id;

    private String name;
    private Set<Person> members;

    public Group() {
    }

    public Group(String name, Set<Person> members) {
        this.name = name;
        this.members = members;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Person> getMembers() {
        return members;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, members);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Group group)) {
            return false;
        }
        return Objects.equals(id, group.id) && Objects.equals(name, group.name) && Objects.equals(members, group.members);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", members=" + members.stream().map(Person::getName).collect(joining(", ")) +
                '}';
    }
}
