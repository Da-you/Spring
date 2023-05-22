package studt.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studt.datajpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
