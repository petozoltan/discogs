package pet.discogs.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Gender gender;
    private Country country;
    private Instrument instrument;
    private Genre genre;

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

    public Gender getGender() {
        return gender;
    }

    public Country getCountry() {
        return country;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public Genre getGenre() {
        return genre;
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
        return "Person" + "{ id=" + id + ", name='" + name + "', gender=" + gender + ", country=" + country + ", instrument=" + instrument + ", genre=" + genre + " }";
    }
}

