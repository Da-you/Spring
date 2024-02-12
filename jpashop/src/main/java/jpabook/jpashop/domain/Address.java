package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

  private String city;
  private String street;
  private String zipcode;

  /**
   * JPA 스펙상 Embeddable나 엔티티는 기본생성자를 proteted로 만들어야 한다.
   * 이런 제약을 두는 이유는 jpa 구현 라이브러리가 객체 생성시 리플렉션 또는 프록시 같은 기술을 사용할 수 있도록 하기 위해서 이다.
   */
  protected Address() {
  }

  public Address(String city, String street, String zipcode) {
    this.city = city;
    this.street = street;
    this.zipcode = zipcode;
  }
}
