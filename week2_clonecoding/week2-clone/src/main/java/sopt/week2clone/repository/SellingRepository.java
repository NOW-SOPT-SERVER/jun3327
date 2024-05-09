package sopt.week2clone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sopt.week2clone.domain.Selling;

import java.util.List;

public interface SellingRepository extends JpaRepository<Selling, Long> {
    List<Selling> findByLocationContaining(String location);
}
