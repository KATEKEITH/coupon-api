package dev.reward.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.reward.domain.Event;
import dev.reward.domain.Reward;
import dev.reward.domain.User;
import dev.reward.excpetion.NotEnoughStockException;
import dev.reward.interfaces.dto.RewardForm;
import dev.reward.interfaces.dto.RewardResultRequest;
import dev.reward.interfaces.dto.RewardResultResponse;
import dev.reward.repository.EventRepository;
import dev.reward.repository.RewardRepository;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class RewardService {

    private final EventRepository eventRepository;

    private final RewardRepository rewardRepository;

    private final UserService userService;

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

}
