package sopt.week2clone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.week2clone.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
