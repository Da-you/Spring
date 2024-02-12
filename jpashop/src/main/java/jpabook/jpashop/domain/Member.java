package jpabook.jpashop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {

  @Id
  @GeneratedValue
  @Column(name = "member_id")
  private Long id;
  @Embedded
  private Address address;
  @OneToMany(mappedBy = "member") // Order에 있는 member의 거울? 읽기 전용?
  private List<Order> orders = new ArrayList<>();

}
