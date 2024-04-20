package org.sopt.practice.service.dto;

import org.sopt.practice.domain.Member;
import org.sopt.practice.domain.Part;

public record MemberFindDto(String name, Part part, int age) {
    public static MemberFindDto of(Member member) {
        return new MemberFindDto(member.getName(), member.getPart(), member.getAge());
    }
}
