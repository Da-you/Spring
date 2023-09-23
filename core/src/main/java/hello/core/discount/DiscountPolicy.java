package hello.core.discount;

import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;

public interface DiscountPolicy {
    int discount(Member member, int price);

}
