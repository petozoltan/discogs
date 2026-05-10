package pet.discogs.data;

import org.springframework.data.jpa.repository.JpaRepository;

interface RecordingRepository extends JpaRepository<Recording, Long> {
}
