package sopt.week2clone.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.week2clone.service.MemberService;
import sopt.week2clone.service.dto.CreateMemberDto;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("")
    public ResponseEntity create(@RequestBody CreateMemberDto createMemberDto) {
        return ResponseEntity.created(URI.create(memberService.createMember(createMemberDto))).build();
    }
}
