package dev.reward.interfaces.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import dev.reward.domain.Reward;
import lombok.Getter;

@Getter
public class RewardResultResponse {

    private final Long id;
    private final Long userId;
    private final String email;
    private final LocalDateTime createdDate;

    public RewardResultResponse(Long id, Long userId, String email, LocalDateTime createdDate) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.createdDate = createdDate;
    }

    public static RewardResultResponse of(Reward reward) {
        return new RewardResultResponse(reward.getId(), reward.getUser().getId(), reward.getUser().getEmail(),
                reward.getCreatedDate());
    }

    public static List<RewardResultResponse> toList(List<Reward> rewards) {
        return rewards.stream()
                .map(reward -> of(reward))
                .collect(Collectors.toList());
    }

}
