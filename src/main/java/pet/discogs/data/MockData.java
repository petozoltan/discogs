package pet.discogs.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pet.discogs.data.group.Group;
import pet.discogs.data.group.GroupRepository;
import pet.discogs.data.person.Person;
import pet.discogs.data.person.PersonRepository;
import pet.discogs.data.recording.Recording;
import pet.discogs.data.recording.RecordingRepository;

import java.util.List;

import static pet.discogs.data.values.Country.*;
import static pet.discogs.data.values.Gender.MALE;
import static pet.discogs.data.values.Genre.JAZZ;
import static pet.discogs.data.values.Genre.ROCK;
import static pet.discogs.data.values.Instrument.*;
import static pet.discogs.data.values.RecordingType.LIVE;
import static pet.discogs.data.values.RecordingType.STUDIO;

@Component
public class MockData implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(MockData.class);

    private final PersonRepository personRepository;
    private final GroupRepository groupRepository;
    private final RecordingRepository recordingRepository;

    @Autowired
    public MockData(PersonRepository personRepository, final GroupRepository groupRepository, final RecordingRepository recordingRepository) {
        this.personRepository = personRepository;
        this.groupRepository = groupRepository;
        this.recordingRepository = recordingRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        initializeH2Db(); // Comment it out to initialize database with data.sql
        printH2DB();
    }

    private void initializeH2Db() {

        Person person01 = new Person("Pat Metheny", MALE, USA, GUITAR, JAZZ);
        Person person02 = new Person("Lyle Mays", MALE, USA, PIANO, JAZZ);
        Person person03 = new Person("Steve Rodby", MALE, USA, BASS, JAZZ);
        Person person04 = new Person("Antonio Sánchez", MALE, USA, DRUMS, JAZZ);

        Person person05 = new Person("Roger Waters", MALE, UK, BASS, ROCK);
        Person person06 = new Person("David Gilmour", MALE, UK, GUITAR, ROCK);
        Person person07 = new Person("Rick Wright", MALE, UK, PIANO, ROCK);
        Person person08 = new Person("Nick Mason", MALE, UK, DRUMS, ROCK);

        Person person09 = new Person("Presser Gábor", MALE, HUNGARY, PIANO, ROCK);
        Person person10 = new Person("Karácsony János", MALE, HUNGARY, GUITAR, ROCK);
        Person person11 = new Person("Somló Tamás", MALE, HUNGARY, BASS, ROCK);
        Person person12 = new Person("Solti János", MALE, HUNGARY, DRUMS, ROCK);

        final List<Person> persons = personRepository.saveAll(List.of(
                person01, person02, person03, person04,
                person05, person06, person07, person08,
                person09, person10, person11, person12
        ));

        Group group01 = new Group("Pat Metheny Group");
        Group group02 = new Group("Pink Floyd");
        Group group03 = new Group("Locomotiv GT");

        final List<Group> groups = groupRepository.saveAll(List.of(group01, group02, group03));

        final Recording recording01 = new Recording("Offramp", 1982, STUDIO);
        final Recording recording02 = new Recording("First Circle", 1984, STUDIO);
        final Recording recording03 = new Recording("Travels", 1983, LIVE);

        final Recording recording04 = new Recording("The Dark Side of the Moon", 1973, STUDIO);
        final Recording recording05 = new Recording("Wish You Were Here", 1975, STUDIO);
        final Recording recording06 = new Recording("The Wall", 1979, STUDIO);

        final Recording recording07 = new Recording("Mindenki", 1978, STUDIO);
        final Recording recording08 = new Recording("Loksi", 1980, STUDIO);
        final Recording recording09 = new Recording("Búcsúkoncert", 1992, LIVE);

        final List<Recording> recordings = recordingRepository.saveAll(List.of(
                recording01, recording02, recording03,
                recording04, recording05, recording06,
                recording07, recording08, recording09
        ));
    }

    private void printH2DB() {

        final List<Person> persons = personRepository.findAll();
        final List<Group> groups = groupRepository.findAll();
        final List<Recording> recordings = recordingRepository.findAll();

        LOG.info("------------------------");
        LOG.info("H2 Database");
        LOG.info("------------------------");
        persons.stream().map(Person::toString).forEach(LOG::info);
        LOG.info("------------------------");
        groups.stream().map(Group::toString).forEach(LOG::info);
        LOG.info("------------------------");
        recordings.stream().map(Recording::toString).forEach(LOG::info);
        LOG.info("------------------------");
    }
}
