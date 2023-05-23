package studt.datajpa.repository;

import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.w3c.dom.stylesheets.LinkStyle;
import studt.datajpa.dto.MemberDto;
import studt.datajpa.entity.Member;

import javax.persistence.NamedStoredProcedureQueries;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findMember(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new studt.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m form Member m where m.username in :names")
    List<Member> findByNames(@Param("names ") List<String> names);

    List<Member>findListByUsername(String username); //컬렉션
    Optional<Member> findOptionalByUsername(String username); //단건
    Member findMemberByUsername(String username); //단건


}
