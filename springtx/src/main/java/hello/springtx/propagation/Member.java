package hello.springtx.propagation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Entity
@Getter
@Service
public class Member {
  @Id
  @GeneratedValue
  private Long id;
  private String username;

  public Member() {
  }

  public Member(String username) {
    this.username = username;
  }
}
