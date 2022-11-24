package dev.reward.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.reward.domain.Event;
import dev.reward.domain.Reward;
import dev.reward.domain.User;
import dev.reward.interfaces.dto.RewardForm;
import dev.reward.interfaces.dto.RewardResultRequest;
import dev.reward.interfaces.dto.RewardResultResponse;
import dev.reward.repository.RewardRepository;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class RewardService {

    private final RewardRepository rewardRepository;

    public Long register(User user, RewardForm rewardForm) {
        checkDuplicateAppy(user);
        Reward reward = new Reward().writtenBy(user);
        Reward savedReward = rewardRepository.save(reward);
        return savedReward.getId();
    }

    @Transactional(readOnly = true)
    public List<RewardResultResponse> search(RewardResultRequest rewardResultRequest) {
        LocalDateTime formatLocalDateTimeNow = LocalDateTime.parse(rewardResultRequest.getDate() + " 00:00:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<Reward> rewards = rewardRepository
                .findAllByCreatedDateGreaterThanEqualOrderByCreatedDateAsc(formatLocalDateTimeNow);

        return RewardResultResponse.toList(rewards);
    }

    private void checkDuplicateAppy(User user) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime ldt = LocalDateTime.of(now.getYear(),
                now.getMonth(), now.getDayOfMonth(), 0, 0, 0);
        List<Reward> reward = rewardRepository.findAllByUserAndCreatedDateGreaterThanEqual(user, ldt);
        if (!reward.isEmpty()) {
            throw new IllegalStateException("이미 참여한 이메일입니다.");
        }

    }

    public boolean checkDays(Long userId, Integer days) {
        String startDate = LocalDateTime.now().minusDays(days).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<Reward> rewards = rewardRepository.findByCreatedDateBetween(startDate, endDate);
        if (rewards.size() == days + 1) {
            return true;
        } else {
            return false;
        }
    }

    public Long checkPoint(User user, Event event, Long point) {
        LocalDate today = LocalDateTime.now().toLocalDate();
        Period diff = Period.between(event.getCreatedDate().toLocalDate(), today);

        if (Math.floorMod(diff.getDays(), 10) == 2) {
            if (checkDays(user.getId(), 2)) {
                point += 300L;
            }
        } else if (Math.floorMod(diff.getDays(), 10) == 4) {
            if (checkDays(user.getId(), 4)) {
                point += 500L;
            }
        } else if (Math.floorMod(diff.getDays(), 10) == 9) {
            if (checkDays(user.getId(), 9)) {
                point += 1000L;
            }
        }

        return point;
    }

}
