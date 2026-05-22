package pet.discogs;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "pet.discogs.data")
@EntityScan("pet.discogs.data")
class DiscogsApplicationConfiguration {

}
