package pet.discogs.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GetService {

	// ---------------------------------------
	// Empty
	// ---------------------------------------

	@GetMapping("/")
	public String index() {
		return "Hello!";
	}

	// ---------------------------------------
	// String
	// ---------------------------------------

	@GetMapping("/hello")
	public String hello(@RequestParam(defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@RequestMapping("/hello2")
	public String hello2(@RequestParam(defaultValue = "World") String name) {
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

	public record GreetingClass(long id, String content) {


	}

	@GetMapping("/greetingc")
	public GreetingClass greetingc(@RequestParam(defaultValue = "World") String name) {
		return new GreetingClass(counter.incrementAndGet(), template.formatted(name));
	}
}
