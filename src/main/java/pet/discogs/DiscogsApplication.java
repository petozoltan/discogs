package pet.discogs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DiscogsApplication {

    /**
     * @implNote The {@link #main(String[])} method will be started 2 times.
     * Once by the Java Application start, and once by the Spring Restarter.
     * <p>
     * Do not put any initializations here.
     * Add initializations into {@link org.springframework.boot.CommandLineRunner}s.
     */
    static void main(String[] args) {
        SpringApplication.run(DiscogsApplication.class, args);
    }
}
