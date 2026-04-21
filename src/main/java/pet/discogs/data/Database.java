package pet.discogs.data;

import static pet.discogs.data.Model.Country.USA;
import static pet.discogs.data.Model.Gender.MALE;
import static pet.discogs.data.Model.Genre.JAZZ;
import static pet.discogs.data.Model.Instrument.GUITAR;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pet.discogs.data.Model.Group;
import pet.discogs.data.Model.Person;
import pet.discogs.data.Model.Recording;

public enum Database {

	DB;

	private final Map<Integer, Person> persons = new HashMap<>();
	private final Map<Integer, Group> groups = new HashMap<>();
	private final Map<Integer, Recording> recordings = new HashMap<>();

	public Collection<Person> getPersons() {
		return persons.values();
	}

	public Person getPerson(Integer id) {
		return persons.get(id);
	}

	public Collection<Group> getGroups() {
		return groups.values();
	}

	public Group getGroup(Integer id) {
		return groups.get(id);
	}

	public Collection<Recording> getRecordings() {
		return recordings.values();
	}

	public Recording getRecording(Integer id) {
		return recordings.get(id);
	}

	private Database() {

		Person p1 = new Person(1, MALE, USA, GUITAR, JAZZ, "Pat Metheny");
	}
}
