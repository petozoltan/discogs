package pet.discogs.data.person;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;
import pet.discogs.data.common.Printable;
import pet.discogs.data.group.Group;
import pet.discogs.data.values.Country;
import pet.discogs.data.values.Gender;
import pet.discogs.data.values.Genre;
import pet.discogs.data.values.Instrument;

import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Person extends Printable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NaturalId
    @Column(unique = true, nullable = false, updatable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Country country;

    @Enumerated(EnumType.STRING)
    private Instrument instrument;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @JsonBackReference("group-member")
    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER)
    private SortedSet<Group> groups = new TreeSet<>();

    public Person() {
    }

    public Person(String name, Gender gender, Country country, Instrument instrument, Genre genre) {
        this.name = name;
        this.gender = gender;
        this.country = country;
        this.instrument = instrument;
        this.genre = genre;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(final Gender gender) {
        this.gender = gender;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(final Country country) {
        this.country = country;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(final Instrument instrument) {
        this.instrument = instrument;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(final Genre genre) {
        this.genre = genre;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, gender, country, instrument, genre);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Person person)) {
            return false;
        }
        return Objects.equals(id, person.id) && Objects.equals(name, person.name) && gender == person.gender && country == person.country && instrument == person.instrument && genre == person.genre;
    }

    @Override
    public String toString() {
        return "Person" +
                "{ id=" + id +
                ", name=" + name +
                ", gender=" + gender +
                ", country=" + country +
                ", instrument=" + instrument +
                ", genre=" + genre +
                ", groups=" + print(groups) +
                " }";
    }

    @Override
    public String getShortName() {
        return name;
    }
}

