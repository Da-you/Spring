package jpabook.jpashop.api;

import static java.util.stream.Collectors.*;

import java.time.LocalDateTime;
import java.util.List;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.query.OrderQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

  private final OrderRepository orderRepository;
  private final OrderQueryRepository orderQueryRepository;


  @GetMapping("/api/v1/orders")
  public List<Order> ordersV1() {
    List<Order> all = orderRepository.findAllByString(new OrderSearch());
    for (Order order : all) {
      order.getMember();
      order.getDelivery().getAddress();

      List<OrderItem> orderItems = order.getOrderItems();
      orderItems.stream().forEach(orderItem -> orderItem.getItem().getName());
    }
    System.out.println(all);
    return all;
  }

  @GetMapping("/api/v2/orders")
  public List<OrderDto> ordersV2() {
    List<Order> orders = orderRepository.findAllByString(new OrderSearch());
    return orders.stream().map(OrderDto::new).collect(toList());
  }

  @GetMapping("/api/v3/orders")  // 페치 조인 적용
  // 1 : N 을 페지 조인 하는 순간 페이징쿼리 불가능하니 일대다에서는 페치 조인 금지
  // 일대다 (컬렉션)페치조인은 1개만 사용가능
  public List<OrderDto> ordersV3() {
    List<Order> orders = orderRepository.findAllWithItem();
    return orders.stream().map(OrderDto::new).collect(toList());
  }

  @GetMapping("/api/v3.1/orders")  // 페치 조인 적용
  // 1 : N 을 페지 조인 하는 순간 페이징쿼리 불가능하니 일대다에서는 페치 조인 금지
  // 일대다 (컬렉션)페치조인은 1개만 사용가능
  public List<OrderDto> ordersV3_page(
      @RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "limit", defaultValue = "100") int limit
  ) {
    List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);
    List<OrderDto> result = orders.stream().map(OrderDto::new).collect(toList());
    return result;
  }

  @GetMapping("/api/v4/orders")
  public List<OrderQueryDto> ordersV4(){
    return orderQueryRepository.findOrderQueryDtos();

  }

  @Getter
  static class OrderDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems;

    public OrderDto(Order order) {
      orderId = order.getId();
      name = order.getMember().getName();
      orderDate = order.getOrderDate();
      orderStatus = order.getOrderStatus();
//      order.getOrderItems().stream().forEach(orderItem -> orderItem.getItem().getName()); //프록시 초기화
//      orderItems = order.getOrderItems();
      address = order.getDelivery().getAddress();
      orderItems = order.getOrderItems().stream().map(orderItem -> new OrderItemDto(orderItem))
          .collect(toList());

    }
  }

  @Getter
  static class OrderItemDto {

    private String name;
    private int orderPrice;
    private int count;


    public OrderItemDto(OrderItem orderItem) {
      name = orderItem.getItem().getName();
      orderPrice = orderItem.getOrderPrice();
      count = orderItem.getCount();
    }
  }

}
