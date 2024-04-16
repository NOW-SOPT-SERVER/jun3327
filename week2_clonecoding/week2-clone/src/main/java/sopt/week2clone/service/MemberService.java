package sopt.week2clone.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopt.week2clone.domain.Member;
import sopt.week2clone.repository.MemberRepository;
import sopt.week2clone.service.dto.CreateMemberDto;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public String createMember(CreateMemberDto createMemberDto) {
        Member saveMember = memberRepository.save(Member.createOne(createMemberDto));
        return saveMember.getId().toString();
    }
}
