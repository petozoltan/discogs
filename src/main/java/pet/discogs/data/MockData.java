package pet.discogs.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pet.discogs.data.person.Person;
import pet.discogs.data.person.PersonRepository;

import java.util.List;

import static pet.discogs.data.values.Country.*;
import static pet.discogs.data.values.Gender.MALE;
import static pet.discogs.data.values.Genre.JAZZ;
import static pet.discogs.data.values.Genre.ROCK;
import static pet.discogs.data.values.Instrument.*;

@Component
public class MockData implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(MockData.class);

    private final PersonRepository personRepository;
//    private final GroupRepository groupRepository;
//    private final RecordingRepository recordingRepository;

    @Autowired
    public MockData(PersonRepository personRepository /*, final GroupRepository groupRepository, final RecordingRepository recordingRepository*/) {
        this.personRepository = personRepository;
//        this.groupRepository = groupRepository;
//        this.recordingRepository = recordingRepository;
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

//        Group group01 = groupRepository.save(new Group("Pat Metheny Group", Set.of(person01, person02, person03, person04)));
//        Group group02 = groupRepository.save(new Group("Pink Floyd", Set.of(person05, person06, person07, person08)));
//        Group group03 = groupRepository.save(new Group("Locomotiv GT", Set.of(person09, person10, person11, person12)));

//        recordingRepository.save(new Recording("Offramp", group01, 1982, STUDIO));
//        recordingRepository.save(new Recording("First Circle", group01, 1984, STUDIO));
//        recordingRepository.save(new Recording("Travels", group01, 1983, LIVE));

//        recordingRepository.save(new Recording("The Dark Side of the Moon", group02, 1973, STUDIO));
//        recordingRepository.save(new Recording("Wish You Were Here", group02, 1975, STUDIO));
//        recordingRepository.save(new Recording("The Wall", group02, 1979, STUDIO));

//        recordingRepository.save(new Recording("Mindenki", group03, 1978, STUDIO));
//        recordingRepository.save(new Recording("Loksi", group03, 1980, STUDIO));
//        recordingRepository.save(new Recording("Búcsúkoncert", group03, 1992, LIVE));
    }

    private void printH2DB() {

        List<Person> persons = personRepository.findAll();
//        final List<Group> groups = groupRepository.findAll();
//        final List<Recording> recordings = recordingRepository.findAll();

        LOG.info("------------------------");
        LOG.info("H2 Database");
        LOG.info("------------------------");
        persons.stream().map(Person::toString).forEach(LOG::info);
        LOG.info("------------------------");
//        LOG.info(groups.toString());
//        LOG.info("------------------------");
//        LOG.info(recordings.toString());
//        LOG.info("------------------------");
    }
}
