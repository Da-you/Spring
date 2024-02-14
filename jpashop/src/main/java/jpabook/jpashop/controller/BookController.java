package jpabook.jpashop.controller;

import java.util.List;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.service.ItemService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class BookController {

  private final ItemService itemService;


  @GetMapping("/items/new")
  public String createForm(Model model) {
    model.addAttribute("form", new BookForm());
    return "items/createItemForm";
  }

  @PostMapping("/items/new")
  public String create(BookForm form) {

    Book book = new Book();
    book.setId(form.getId());
    book.setName(form.getName());
    book.setPrice(form.getPrice());
    book.setStockQuantity(form.getStockQuantity());
    book.setAuthor(form.getAuthor());
    book.setIsbn(form.getIsbn());

    itemService.save(book);

    return "redirect:/items";
  }

  @GetMapping("/items")
  public String list(Model model) {
    List<Item> items = itemService.items();
    model.addAttribute("items", items);
    return "items/itemList";
  }

  @GetMapping("/items/{itemId}/edit")
  public String updateItemForm(@PathVariable Long itemId, Model model) {
    Book item = (Book) itemService.findOne(itemId);

    BookForm form = new BookForm();
    form.setId(item.getId());
    form.setName(item.getName());
    form.setPrice(item.getPrice());
    form.setStockQuantity(item.getStockQuantity());
    form.setAuthor(item.getAuthor());
    form.setIsbn(item.getIsbn());

    model.addAttribute("form", form);
    return "items/updateItemForm";
  }

  /**
   * 병합보다는 변경감지를 사용해서 데이터를 변경하라
   * 병합을 사용시 데이터에 값이 비어있을 경우 null이 그대로 데이터에 반영이 된다
   * 컨트롤러단에서는 어설프 엔티티를 생성하지 마라
   * 트랜잭션이 있는 서비스 계층에서 식별자(id)와 변경할 데이터를 명확하게 전달하라(파라미터 or DTO)
   * 트랜잭션이 있는 서비스 계층에서 영속 상태의 엔티티를 조회하고 엔티티를 직접 변경하라
   */
  @PostMapping("/items/{itemId}/edit")
  public String updateItem(@ModelAttribute BookForm form, @PathVariable Long itemId) {
//    Book book = new Book();
//    book.setId(form.getId());
//    book.setName(form.getName());
//    book.setPrice(form.getPrice());
//    book.setStockQuantity(form.getStockQuantity());
//    book.setAuthor(form.getAuthor());
//    book.setIsbn(form.getIsbn());
//    itemService.save(book);
    itemService.udateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());

    return "redirect:/items";
  }


}
