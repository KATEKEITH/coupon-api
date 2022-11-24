package dev.reward.interfaces.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RewardResultRequest {

    @Pattern(regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$")
    @NotBlank(message = "YYYY-DD-MM 형식으로 입력해주세요 :)")
    private String date;

    public RewardResultRequest(String date) {
        this.date = date;
    }

}
