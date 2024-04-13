package org.sopt.practice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.sopt.practice.domain.Member;
import org.sopt.practice.repository.MemberRepository;
import org.sopt.practice.service.dto.MemberCreateDto;
import org.sopt.practice.service.dto.MemberFindDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public String createMember(final MemberCreateDto createDto) {
        Member member = Member.create(createDto.name(), createDto.part(), createDto.age());
        memberRepository.save(member);
        return member.getId().toString();
    }

//    @Transactional(readOnly = true)
    public MemberFindDto findMemberById(Long id) {
        return MemberFindDto.of(memberRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("ID에 해당하는 사용자가 없네여~ㅋㅋ")
        ));
    }

    // 모든 등록된 회원 정보 List로 반환
    public List<MemberFindDto> findMemberList() {
        List<Member> memberList = memberRepository.findAll();

        return new ArrayList<>(memberList.stream()
                .map(member -> MemberFindDto.of(member))
                .toList());
    }

    @Transactional
    public void deleteMemberById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("ID에 해당하는 사용자가 없음"));
        memberRepository.delete(member);
    }
}
