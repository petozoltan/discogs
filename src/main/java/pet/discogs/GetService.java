package pet.discogs;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetService {

	// ---------------------------------------
	// Empty
	// ---------------------------------------

	@GetMapping("/")
	public String index() {
		return String.format("Hello!");
	}

	// ---------------------------------------
	// String
	// ---------------------------------------

	
	
	@GetMapping("/hello")
	public String hello(@RequestParam(defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	// ---------------------------------------
	// Record
	// ---------------------------------------

	public record Greeting(long id, String content) {
	}

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), template.formatted(name));
	}

	// ---------------------------------------
	// Class
	// ---------------------------------------

	public class GreetingClass {
		
		long id;
		String content;

		public GreetingClass(long id, String content) {
			this.id = id;
			this.content = content;
		}

		public long getId() {
			return id;
		}

		public String getContent() {
			return content;
		}
	}
	

	@GetMapping("/greetingc")
	public GreetingClass greetingc(@RequestParam(defaultValue = "World") String name) {
		return new GreetingClass(counter.incrementAndGet(), template.formatted(name));
	}
}
