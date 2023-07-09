package hello.servlet.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L; // 0, 1, 2, ... 키값 생성

    private static final MemberRepository instance = new MemberRepository();
    public static MemberRepository getInstance() {
        return instance;
    }

    private MemberRepository() { // 외부에서 생성하지 못하도록 private으로 생성자를 막음
    }

   public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
   }
   public Member findById(Long id) {
        return store.get(id);
   }
   public List<Member> findAll() {
        return new ArrayList<>(store.values()); // store에 있는 모든 값들을 새로운 ArrayList에 담아서 반환
   }

   public void clearstore() {
        store.clear();
   }
}
