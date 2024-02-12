package jpabook.jpashop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;
import jpabook.jpashop.exception.NotEnaughStock;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

  @Id
  @GeneratedValue
  @Column(name = "item")
  private Long id;

  private String name;
  private int price;
  private int stockQuantity;
  @ManyToMany(mappedBy = "items")
  private List<Category> categories = new ArrayList<>();

  //== 비즈니스 로직 ==//

  /**
   * stock 증가
   */
  public void addStock(int quantity){
    this.stockQuantity += quantity;
  }

  /**
   * stock 감소
   */
  public void reduceStock(int quantiy){
    int restStock = this.stockQuantity - quantiy;
    if (restStock < 0){
      throw new NotEnaughStock("재고가 부족합니다.");
    }
    this.stockQuantity = restStock;

  }

}
