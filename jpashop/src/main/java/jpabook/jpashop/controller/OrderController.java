package jpabook.jpashop.controller;

import java.util.List;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;
  private final MemberService memberService;
  private final ItemService itemService;

  @GetMapping("/order")
  public String createOrderForm(Model model) {
    List<Member> members = memberService.members();
    List<Item> items = itemService.items();

    model.addAttribute("members", members);
    model.addAttribute("items", items);
    return "order/orderForm";
  }

  /**
   * 엔티티의 조회의 경수 서비스 계층에서 조회하라 서비스 계층에서는 트랜잭션을 이용하여 영속성컨텍스트의 관리를 받는 엔티티를 이용할수 있다.
   */
  @PostMapping("/order")
  public String order(@RequestParam("memberId") Long memberId,
      @RequestParam("itemId") Long itemId,
      @RequestParam("count") int count) {

    orderService.order(memberId, itemId, count);

    return "redirect:/orders";
  }

  @GetMapping("/orders")
  public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
    List<Order> orders = orderService.findOrders(orderSearch);
    model.addAttribute("orders", orders);

    return "order/orderList";
  }

  @PostMapping("/orders/{orderId}/cancel")
  public String cancelOrder(@PathVariable Long orderId) {
    orderService.cancelOrder(orderId);
    return "redirect:/orders";
  }

}
