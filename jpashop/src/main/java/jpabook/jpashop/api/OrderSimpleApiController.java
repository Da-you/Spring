package jpabook.jpashop.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * xToOne(ManyToOne, OneToOne) Order Order -> Member Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

  private final OrderRepository orderRepository;

  @GetMapping("/api/v1/simple-orders")
  public List<Order> ordersV1() {
    List<Order> all = orderRepository.findAllByString(new OrderSearch());
    return all;  // 무한 루프 발생 api
  }

  // order, member, delivery 총 3번의 쿼리인줄 알았으나 5번의 쿼리
  @GetMapping("/api/v2/simple-orders")
  public List<SimpleOrderDto> ordersV2() {
    // order 2개 조회
    // N+ 1 -> 1 + N(member, delivery 총 두번)
    List<Order> orders = orderRepository.findAllByString(new OrderSearch());
    List<SimpleOrderDto> result = orders.stream()
        .map(o -> new SimpleOrderDto(o))
        .collect(Collectors.toList());
    return result;
  }

  // 1번의 쿼리 발생
  @GetMapping("/api/v3/simple-orders")
  public List<SimpleOrderDto> ordersV3() {
    List<Order> orders = orderRepository.findAllWithMemberDelivery();
    List<SimpleOrderDto> result = orders.stream()
        .map(o -> new SimpleOrderDto(o))
        .collect(Collectors.toList());
    return result;

  }


  @Data
  static class SimpleOrderDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public SimpleOrderDto(Order order) {
      orderId = order.getId();
      name = order.getMember().getName(); // lazy초기화
      orderDate = order.getOrderDate();
      orderStatus = order.getStatus();
      address = order.getDelivery().getAddress(); // lazy초기화
    }
  }

}
