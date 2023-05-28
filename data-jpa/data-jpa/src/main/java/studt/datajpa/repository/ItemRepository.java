package studt.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import studt.datajpa.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{
}
