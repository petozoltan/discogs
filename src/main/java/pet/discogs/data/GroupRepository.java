package pet.discogs.data;

import org.springframework.data.jpa.repository.JpaRepository;

interface GroupRepository extends JpaRepository<Group, Long> {
}
