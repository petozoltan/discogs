package pet.discogs;

import pet.discogs.data.MockData;

//@SpringBootApplication
public class DiscogsApplication {

	public static void main(String[] args) {
		MockData.initializeDb();
//		System.out.println(DB.getPersons());
//		SpringApplication.run(DiscogsApplication.class, args);
	}

}
