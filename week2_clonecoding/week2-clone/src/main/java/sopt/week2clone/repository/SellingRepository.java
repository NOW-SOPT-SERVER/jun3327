package sopt.week2clone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.week2clone.domain.Selling;

public interface SellingRepository extends JpaRepository<Selling, Long> {
}
