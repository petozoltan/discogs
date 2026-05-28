# TODO

## Database

- [ ] Use PostgreSQL.

## Java

### Java 23

- [ ] Convert javadoc comment to Markdown (with IDE action).

## Spring Boot

### REST

- [ ] Add global error page for 404 and 500 errors.
- [ ] Fix `hibernateLazyInitializer` in JSON response.
- [ ] Fix endless recursion in JSON response with `@JsonIgnore` and `@JsonManagedReference`/`@JsonBackReference`.
- [ ] Add Spring Security.

## Spring Data

### JPA

#### Entities

- [x] Create valid column name with JPA
- [ ] Set up JPA relations between entities.
- [ ] Add `@NaturalId` with multiple attributes.
- [ ] Add `@Transient` attribute.
- [ ] Add `@Temporal` attribute.
- [x] Optimize `save` actions for database initialization in Java. (No single persisting.)
- [ ] Add related entities with lazy loading.
- [ ] Implement `equals()` and `hashCode() with correct IDs. (Generated or Natural or any members.)
- [ ] Create a composite key with `@IdClass` or `@EmbeddedId` and `@Embeddable`.
- [ ] Add `@Version` for optimistic locking.

##### Queries

- [ ] Add custom query methods.
- [ ] Solve N+1 problem with `@EntityGraph` and `JOIN FETCH`.

Derived Query Methods

- [ ] Add Nested attribute queries (with entity relations).
- [ ] Add pagination.
- [ ] Add sorting.
- [ ] Add projections.
- [ ] Add events.

### REST

- [ ] Use Spring Data REST.

-----------------------------------------------------------------------------------

# Questions

## JPA

### Entities

- [x] How to avoid concurrent modification exception when saving related entities?
- [x] Fix invalid colunm name with JPA annotation.
- [x] Should I initialize collection attributes with `new ArrayList<>()` (or `Set` or `Map`)?

#### Generated IDs

- [ ] How to create related entities with SQL, when IDs are auto-generated?

#### Save

- [ ] How to enable 'batch mode' for `saveAll()`?

## REST

- [ ] What does `@RequestMapping("/")` mean on a REST controller class?

-----------------------------------------------------------------------------------

# Learned

## Java

### Java 16

- `Stream.toList()` can be used instead of `.collect(toList())`.
    - `Stream.toList()` → Immutable, value-only, optimized
    - `Collectors.toList()` → Mutable, identity-based, traditional

### Java 23

#### Javadoc

- After Java 23 or IntelliJ 2024.2 Markdown javadoc comments can be used.
- Recommended replacement for `{@link Class#method(parameters)}` is `[Class#method(parameters)]`.
- Traditional javadoc tags are still supported.

## JPA

### Entities

#### Generated IDs

- The default strategy is `AUTO`, which is most likely `SEQUENCE`.

    - `SEQUENCE`
        - DOES: Uses a sequence in the database, and allocates Ids in chunks.
        - PROS: Works well with batch inserts, and does not require a round trip to the database for each Id generation.
        - CONS: Requires a sequence in the database, and may not work well with some databases that do not support
          sequences.
    - `IDENTITY`
        - DOES: Uses the database's identity column, and allocates Ids one at a time.
        - PROS: Works with all databases, and does not require a sequence.
        - CONS: May not work well with batch inserts, and requires a round trip to the database for each Id generation.

#### Save

- `save()` is used for both `persist()` and `merge()` and Spring JPA decides which one to execute.
    - Typically by `id == null`.
- `saveAll()` executes many single `save()` but within one transaction.
- No need to call it explicitly when modifying a managed entity within a transaction.
    - Not even before a `find` method.

#### One-to-Many/Many-to-One

- Cross-link the entities on both sides before persisting.
- Persist only that side of the relation which has the `cascade = CascadeType.PERSIST`.
- Don't include both sides of the relation in the `hasCode()`, `equals()` and `toString()` methods, to avoid infinite
  recursion.
    - Include only the foreign key side of the relation in the `hasCode()`, `equals()` and `toString()` methods.
- Initialize collection attributes with `new ArrayList<>()` (or `Set` or `Map`) to avoid `NullPointerException`.
    - Use only mutable collections for collection attributes, to avoid `UnsupportedOperationException`.
- Foreign key column names are derived from the attribute name by default, but can be customized with `@JoinColumn`.
- `CascadeType.REMOVE` and `OneToMany.orphanRemoval` both control deletion of child entities, but they trigger in
  different situations

#### Many-to-Many

- Prefer Set over List — Hibernate generates a delete all + reinsert for List on any change (the "bag" problem).
- Don't use CascadeType.REMOVE.

### Queries

#### Derived Query Methods

Find the naming convention for the possible query methods here:

- [Repository query keywords](https://docs.spring.io/spring-data/rest/reference/data-commons/repositories/query-keywords-reference.html)

`@Transactional`

- `SimpleJpaRepository` adds it as necessary.
- Every method has a read-only transaction is by default, unless it is overridden.
- No need to add it to the class, to inherited methods, to derived methods.
- Add it only to custom methods that modify data.
- Add it to the service layer.
- Add it to integration tests.

## Spring Boot

### Application

#### Application class

- The `main(String[])}` method will be started 2 times: once by the Java Application start, once by the Spring
  Restarter.
- Do not put any initializations into that.

#### Configuration

- It is optional to create a distinct class with `@Configuration` because `@SpringBootApplication` is a meta-annotation
  that already adds `@Configuration`.
- It is optional to add `@EnableJpaRepositories("path")` and `@EntityScan("path"), because all packages are scanned by
  default.

### Database

#### Initialization

Don't:

- Do not put any initializations into the `main(String[])}` method.
- It will be started 2 times: once by the Java Application start, once by the Spring Restarter.

With Java:

- Add initializations into a `@Component` that implements a `Runner`:
    - `ApplicationRunner` offers a better access to the command line arguments of the Java application.
    - `CommandLineRunner`.

With SQL:

- Add initialization SQLcommands to a file named `classpath:data.sql`.
- This file will be picked up by Spring Boot's auto-configuration.
- It will be executed before the table creation DDL commands.
    - For an in-memory database use `spring.jpa.defer-datasource-initialization: true` in `application.yaml`.
- It runs after `classpath:data.sql`, which is intended for DDL commands.
- It can be turned on/off by `spring.sql.init.mode` in `application.yaml`.
    - Default is `embedded`, which is executed in case of an in-memory database (e.g. H2).

#### H2

- Default access of the H2 Console of an in-memory H2 database: http://localhost:8080/h2-console /
  `jdbc:h2:mem:testdb` / `sa` / (no password)
- `application.yaml` settings to change them: `spring.h2.console`, `spring.datasource` and
  `spring.jpa.database-platform`.
    - Recommended for Hibernate: `org.hibernate.dialect.H2Dialect`
- See [Spring Boot With H2 Database](https://www.baeldung.com/spring-boot-h2-database)

-----------------------------------------------------------------------------------

# Courses

## Learn Spring Data JPA course

### Module 1 - Getting Started with Spring Data JPA

#### Lesson 3: The Persistence Project We’re Building – part 1

- [x] `@NaturalId` added to some Entities.

### Module 2 - Spring Data Repositories

#### Lesson 1: Spring Data JPA Setup

- [x] Some Spring configuration annotations added

#### Lesson 3: Derived Query Methods

- [x] Some simple derived query methods added to the repository interfaces.

#### Lesson 4: Derived Query Methods with Multiple Parameters

- [x] Some more complex derived query methods added to the repository interfaces.

#### Lesson 5: Spring Data JPA Save Methods

- [x] `saveAll()` used for database initialization in Java.