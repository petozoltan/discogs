FROM eclipse-temurin:25
COPY ./target/discogs-0.0.1-SNAPSHOT.jar /app/discogs.jar
WORKDIR /app
EXPOSE  8085
ENTRYPOINT ["java", "-jar", "discogs.jar"]
CMD ["preloaddata"]

# Alternative base image:
# FROM eclipse-temurin:25-jre-jammy

# Build & Run:
#   cd to discogs root
# Build application:
#   mvn clean package
# Build image (N is the version number):
#   docker build -t discogs:N-<base image tag> .
#   docker image ls -a
# Create container (with pppp as any port number):
#   docker create -p pppp:8085 discogs:N-<base image tag>
#   docker container ls -a
# Start container:
#   docker start -i <container id or name>
#   docker container ls (or docker ps)
# Test
#   curl http://localhost:pppp/persons
#   curl http://localhost:pppp/groups
#   curl http://localhost:pppp/recordings
#   curl http://localhost:pppp/h2


# Alternatives:
# Create and start and remove container:
#   docker run -p pppp:8085 --rm discogs:N-<base image tag>
# Create and start and remove container with volume:
#   docker run -p pppp:8085 --rm -v /path/to/local/folder:/app/data discogs:N-<base image tag>

# TODO
# H2 Console: Sorry, remote connections ('webAllowOthers') are disabled on this server.