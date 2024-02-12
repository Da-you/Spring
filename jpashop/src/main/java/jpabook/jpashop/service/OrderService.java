package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final MemberRepository memberRepository;
  private final ItemRepository itemRepository;

  /**
   * 주문 생성 delivery와 orderItem에 각 save 메서드가 없어도 Order에 매핑이 되는 이유 order에서 delivery와 orderItem에 설정한
   * Cascade설정으로 order를 persist할때 delivery와 orderItem도 persist가 날라간다
   */
  public Long order(Long memberId, Long itemId, int count) {

    //엔티티 조회
    Member member = memberRepository.find(memberId);
    Item item = itemRepository.findOne(itemId);

    //배송 정보 생성
    Delivery delivery = new Delivery();
    delivery.setAddress(member.getAddress());

    //주문 상품 생성
    OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

    // 주문 생성
    Order order = Order.createOrder(member, delivery, orderItem);
    orderRepository.save(order);
    return order.getId();
  }

  //취소
  public void cancelOrder(Long orderId) {
    Order order = orderRepository.findOne(orderId);
    order.cancel();
  }

  //검색
//  public List<Order> findOrders(OrderSearch orderSearch) {
//    return orderRepository.findALl(orderSearch);
//  }

}
