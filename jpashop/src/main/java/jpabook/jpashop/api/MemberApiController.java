package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

  private final MemberService memberService;

  @GetMapping("api/v1/members")
  public List<Member> membersV1() {
    List<Member> members = memberService.members();
    return members;
  }

  @GetMapping("api/v2/members")
  public Result membersV2() {
    List<Member> findMembers = memberService.members();
    List<MemberDto> collect = findMembers.stream()
        .map(member -> new MemberDto(member.getName()))
        .collect(Collectors.toList());
    return new Result(collect);
  }

  @Data
  @AllArgsConstructor
  static class Result<T> {

    private T data;
  }

  @Data
  @AllArgsConstructor
  static class MemberDto {

    private String name;
  }

  @PostMapping("/api/v1/members")
  public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
    Long id = memberService.join(member);
    return new CreateMemberResponse(id);
  }

  @PostMapping("/api/v2/members")
  public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
    Member member = new Member();
    member.setName(request.name);
    return new CreateMemberResponse(memberService.join(member));
  }

  @PutMapping("/api/v2/members/{id}")
  public UpdateMemberResponse updateMemberV2(
      @PathVariable("id") Long id,
      @RequestBody UpdateMemberRequest request) {
    memberService.update(id, request.getName());
    Member member = memberService.findOne(id);
    return new UpdateMemberResponse(member.getId(), member.getName());
  }

  @Data
  @AllArgsConstructor
  static class UpdateMemberResponse {

    private Long id;
    private String name;
  }

  @Data
  static class UpdateMemberRequest {

    private String name;
  }

  @Data
  static class CreateMemberRequest {

    @NotEmpty
    private String name;
  }

  @Data
  static class CreateMemberResponse {

    private Long id;

    public CreateMemberResponse(Long id) {
      this.id = id;
    }
  }

}
