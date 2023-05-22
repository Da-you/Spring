package studt.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studt.datajpa.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
