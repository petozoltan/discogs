package pet.discogs.data;

import pet.discogs.data.Model.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum Database {

	DB;

	// --------------------------------------
	// Persons
	// --------------------------------------

	private final Map<Integer, Person> persons = new HashMap<>();

	public Collection<Person> getPersons() {
		return persons.values();
	}

	public Person getPerson(Integer id) {
		return persons.get(id);
	}

	public Person createAndAddPerson(String name, Gender gender, Country country, Instrument instrument, Genre genre) {
		Person person = new Person(name, gender, country, instrument, genre);
		persons.put(person.id(), person);
		return person;
	}

	// --------------------------------------
	// Groups
	// --------------------------------------

	private final Map<Integer, Group> groups = new HashMap<>();

	public Collection<Group> getGroups() {
		return groups.values();
	}

	public Group getGroup(Integer id) {
		return groups.get(id);
	}

	public Group createAndAddGroup(String name, Set<Person> members) {
		Group group = new Group(name, members);
		groups.put(group.id(), group);
		return group;
	}

	// --------------------------------------
	// Recordings
	// --------------------------------------

	private final Map<Integer, Recording> recordings = new HashMap<>();

	public Collection<Recording> getRecordings() {
		return recordings.values();
	}

	public Recording getRecording(Integer id) {
		return recordings.get(id);
	}

	public Recording createAndAddRecording(String title, Group group, Integer year, RecordingType type) {
		Recording recording = new Recording(title, group, year, type);
		recordings.put(recording.id(), recording);
		return recording;
	}

	// --------------------------------------
	// Database
	// --------------------------------------

	public void printDatabase() {
		System.out.println(persons);
		System.out.println(groups);
		System.out.println(recordings);
	}
}
