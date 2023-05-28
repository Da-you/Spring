package studt.datajpa.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@MappedSuperclass // 속성만 상속받고 싶을 때 사용
public class JpaBaseEntity {
    @Column(updatable = false)
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }

}
