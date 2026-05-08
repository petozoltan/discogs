package pet.discogs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pet.discogs.data.MockData;

import static pet.discogs.data.Database.DB;

@SpringBootApplication
public class DiscogsApplication {

    static void main(String[] args) {
        MockData.initializeDb();
        IO.println(DB.getPersons());
        SpringApplication.run(DiscogsApplication.class, args);
    }
}
