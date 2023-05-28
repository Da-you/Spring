package studt.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {
   //  @Value("#{target.username + ' ' + target.age}") // SpEL OPen Projection
    String getUsername();
}
