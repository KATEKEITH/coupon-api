package dev.reward.interfaces.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import dev.reward.domain.Reward;
import dev.reward.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RewardForm {
    @NotBlank(message = "이메일은 필수 입니다")
    private String email;

    public RewardForm(String email) {
        this.email = email;
    }

    // public Reward toEntity() {
    // return Reward.builder().email(this.email).build();
    // }
}
