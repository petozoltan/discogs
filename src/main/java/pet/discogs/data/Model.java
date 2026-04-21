package pet.discogs.data;

import java.util.Set;

public class Model {

	public enum Country {
		USA,
		UK,
		GERMANY,
		FRANCE,
		SPAIN,
		HUNGARY
	}

	public enum Gender {
		MALE,
		FEMALE
	}

	public enum Genre {
		CLASSICAL,
		ROCK,
		POP,
		JAZZ
	}

	public enum Instrument {
		PIANO,
		ORGAN,
		SYNTHESIZER,
		GUITAR,
		BASS,
		SAXOPHONE,
		FLUTE,
		VOCAL,
		DRUMS
	}

	public enum RecordingType {
		STUDIO,
		LIVE
	}

	public record Person(int id, Gender gender, Country country, Instrument instrument, Genre genre, String name) {
	}

	public record Group(int id, String name, Set<Person> members) {
	}

	public record Recording(int id, Group group, Integer year, RecordingType type, String title) {
	}
}
