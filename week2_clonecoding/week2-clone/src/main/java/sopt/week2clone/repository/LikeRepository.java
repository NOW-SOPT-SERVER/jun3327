package sopt.week2clone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sopt.week2clone.domain.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("select count(l) from Like l where l.selling.id =: sellingId")
    int findSellingLikeCount(@Param("sellingId") Long sellingId);
}
