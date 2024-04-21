package org.sopt.practice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.sopt.practice.Exception.NotFoundException;
import org.sopt.practice.common.dto.ErrorMessage;
import org.sopt.practice.domain.Member;
import org.sopt.practice.repository.MemberRepository;
import org.sopt.practice.service.dto.MemberCreateDto;
import org.sopt.practice.service.dto.MemberFindDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                () -> new NotFoundException(ErrorMessage.MEMBER_NOT_FOUND)
        ));
    }

    public Member findMemberEntityById(Long id) {
        return memberRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.MEMBER_NOT_FOUND)
        );
    }
    @Transactional
    public void deleteMemberById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.MEMBER_NOT_FOUND));
        memberRepository.delete(member);
    }
}
