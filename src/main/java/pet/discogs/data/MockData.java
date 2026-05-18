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
        initializeH2Db();
        printH2DB();
    }

    private void initializeH2Db() {

        Person person01 = personRepository.save(new Person("Pat Metheny", MALE, USA, GUITAR, JAZZ));
        Person person02 = personRepository.save(new Person("Lyle Mays", MALE, USA, PIANO, JAZZ));
        Person person03 = personRepository.save(new Person("Steve Rodby", MALE, USA, BASS, JAZZ));
        Person person04 = personRepository.save(new Person("Antonio Sánchez", MALE, USA, DRUMS, JAZZ));

        Person person05 = personRepository.save(new Person("Roger Waters", MALE, UK, BASS, ROCK));
        Person person06 = personRepository.save(new Person("David Gilmour", MALE, UK, GUITAR, ROCK));
        Person person07 = personRepository.save(new Person("Rick Wright", MALE, UK, PIANO, ROCK));
        Person person08 = personRepository.save(new Person("Nick Mason", MALE, UK, DRUMS, ROCK));

        Person person09 = personRepository.save(new Person("Presser Gábor", MALE, HUNGARY, PIANO, ROCK));
        Person person10 = personRepository.save(new Person("Karácsony János", MALE, HUNGARY, GUITAR, ROCK));
        Person person11 = personRepository.save(new Person("Somló Tamás", MALE, HUNGARY, BASS, ROCK));
        Person person12 = personRepository.save(new Person("Solti János", MALE, HUNGARY, DRUMS, ROCK));

        Group group01 = groupRepository.save(new Group("Pat Metheny Group"));
        Group group02 = groupRepository.save(new Group("Pink Floyd"));
        Group group03 = groupRepository.save(new Group("Locomotiv GT"));

        recordingRepository.save(new Recording("Offramp", 1982, STUDIO));
        recordingRepository.save(new Recording("First Circle", 1984, STUDIO));
        recordingRepository.save(new Recording("Travels", 1983, LIVE));

        recordingRepository.save(new Recording("The Dark Side of the Moon", 1973, STUDIO));
        recordingRepository.save(new Recording("Wish You Were Here", 1975, STUDIO));
        recordingRepository.save(new Recording("The Wall", 1979, STUDIO));

        recordingRepository.save(new Recording("Mindenki", 1978, STUDIO));
        recordingRepository.save(new Recording("Loksi", 1980, STUDIO));
        recordingRepository.save(new Recording("Búcsúkoncert", 1992, LIVE));
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
