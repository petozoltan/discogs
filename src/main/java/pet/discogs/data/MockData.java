package pet.discogs.data;

import pet.discogs.data.Model.Group;
import pet.discogs.data.Model.Person;

import java.util.Set;

import static pet.discogs.data.Database.DB;
import static pet.discogs.data.Model.Country.*;
import static pet.discogs.data.Model.Gender.MALE;
import static pet.discogs.data.Model.Genre.JAZZ;
import static pet.discogs.data.Model.Genre.ROCK;
import static pet.discogs.data.Model.Instrument.*;
import static pet.discogs.data.Model.RecordingType.LIVE;
import static pet.discogs.data.Model.RecordingType.STUDIO;

public class MockData {

	public static void initializeDb() {

		Person person01 = DB.createAndAddPerson("Pat Metheny", MALE, USA, GUITAR, JAZZ);
		Person person02 = DB.createAndAddPerson("Lyle Mays", MALE, USA, PIANO, JAZZ);
		Person person03 = DB.createAndAddPerson("Steve Rodby", MALE, USA, BASS, JAZZ);
		Person person04 = DB.createAndAddPerson("Antonio Sánchez", MALE, USA, DRUMS, JAZZ);

		Person person05 = DB.createAndAddPerson("Roger Waters", MALE, UK, BASS, ROCK);
		Person person06 = DB.createAndAddPerson("David Gilmour", MALE, UK, GUITAR, ROCK);
		Person person07 = DB.createAndAddPerson("Rick Wright", MALE, UK, PIANO, ROCK);
		Person person08 = DB.createAndAddPerson("Nick Mason", MALE, UK, DRUMS, ROCK);

		Person person09 = DB.createAndAddPerson("Presser Gábor", MALE, HUNGARY, PIANO, ROCK);
		Person person10 = DB.createAndAddPerson("Karácsony János", MALE, HUNGARY, GUITAR, ROCK);
		Person person11 = DB.createAndAddPerson("Somló Tamás", MALE, HUNGARY, BASS, ROCK);
		Person person12 = DB.createAndAddPerson("Solti János", MALE, HUNGARY, DRUMS, ROCK);

		Group group01 = DB.createAndAddGroup("Pat Metheny Group", Set.of(person01, person02, person03, person04));
		Group group02 = DB.createAndAddGroup("Pink Floyd", Set.of(person05, person06, person07, person08));
		Group group03 = DB.createAndAddGroup("Locomotiv GT", Set.of(person09, person10, person11, person12));

		DB.createAndAddRecording("Offramp", group01, 1982, STUDIO);
		DB.createAndAddRecording("First Circle", group01, 1984, STUDIO);
		DB.createAndAddRecording("Travels", group01, 1983, LIVE);

		DB.createAndAddRecording("The Dark Side of the Moon", group02, 1973, STUDIO);
		DB.createAndAddRecording("Wish You Were Here", group02, 1975, STUDIO);
		DB.createAndAddRecording("The Wall", group02, 1979, STUDIO);

		DB.createAndAddRecording("Mindenki", group03, 1978, STUDIO);
		DB.createAndAddRecording("Loksi", group03, 1980, STUDIO);
		DB.createAndAddRecording("Búcsúkoncert", group03, 1992, LIVE);

		DB.printDatabase();
	}

}
