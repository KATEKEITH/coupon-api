package dev.reward.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.reward.domain.Reward;
import dev.reward.domain.User;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {

        List<Reward> findAllByUser(User user);

        List<Reward> findAllByUserAndCreatedDateGreaterThanEqual(User user, LocalDateTime today);

        List<Reward> findAllByCreatedDateGreaterThanEqualOrderByCreatedDateAsc(LocalDateTime today);

        @Query("select distinct reward from Reward as reward join fetch reward.user where reward.createdDate between DATE(:startDate) and DATE(:endDate)+1")
        List<Reward> findByCreatedDateBetween(@Param("startDate") String startDate,
                        @Param("endDate") String endDate);
}
