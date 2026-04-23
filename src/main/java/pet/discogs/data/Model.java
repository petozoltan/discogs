package pet.discogs.data;

import static java.util.stream.Collectors.toSet;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

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

	public record Person(int id, String name, Gender gender, Country country, Instrument instrument, Genre genre) {

		private static AtomicInteger lastId = new AtomicInteger();

		public Person(String name, Gender gender, Country country, Instrument instrument, Genre genre) {
			this(lastId.incrementAndGet(), name, gender, country, instrument, genre);
		}
	}

	public record Group(int id, String name, Set<Person> members) {

		private static AtomicInteger lastId = new AtomicInteger();

		public Group(String name, Set<Person> members) {
			this(lastId.incrementAndGet(), name, members);
		}

		@Override
		public String toString() {
			return "Group" +
					" [id=" + id +
					", name=" + name +
					", members=" + members.stream().map(Person::name).collect(toSet()) + "]";
		}
	}

	public record Recording(int id, String title, Group group, Integer year, RecordingType type) {

		private static AtomicInteger lastId = new AtomicInteger();

		public Recording(String title, Group group, Integer year, RecordingType type) {
			this(lastId.incrementAndGet(), title, group, year, type);
		}

		@Override
		public String toString() {
			return "Recording" +
					" [id=" + id +
					", title=" + title +
					", group=" + group.name() +
					", year=" + year + ", "
					+ "type=" + type + "]";
		}

	}
}
