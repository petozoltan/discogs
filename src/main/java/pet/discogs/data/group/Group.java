package pet.discogs.data.group;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;
import pet.discogs.data.common.Printable;
import pet.discogs.data.person.Person;
import pet.discogs.data.recording.Recording;

import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "BAND")
public class Group extends Printable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NaturalId
    @Column(unique = true, nullable = false, updatable = false)
    private String name;

    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private SortedSet<Recording> recordings = new TreeSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "GROUP_MEMBER",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private SortedSet<Person> members = new TreeSet<>();

    public Group() {
    }

    public Group(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Set<Recording> getRecordings() {
        return recordings;
    }

    public void setRecordings(final Set<Recording> recordings) {
        this.recordings.clear();
        this.recordings.addAll(recordings);
    }

    public Set<Person> getMembers() {
        return members;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Group group)) {
            return false;
        }
        return Objects.equals(id, group.id) && Objects.equals(name, group.name);
    }

    @Override
    public String toString() {
        return "Group" +
                "{ id=" + id +
                ", name='" + name + "'" +
                ", members=" + print(members) /* members.stream().map(Person::getName).toList() */ +
                ", recordings=" + print(recordings)/* recordings.stream().map(Recording::getTitle).toList() */ +
                " }";
    }

    @Override
    public String getShortName() {
        return name;
    }
}
