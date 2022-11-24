package dev.reward.interfaces.dto;

import dev.reward.domain.User;
import lombok.Getter;

@Getter
public class DetailResponse {

    private final String email;
    private final Long point;

    public DetailResponse(String email, Long point) {
        // this.id = id;
        this.email = email;
        this.point = point;
    }

    public static DetailResponse of(User user) {
        return new DetailResponse(user.getEmail(), user.getPoint());
    }

}
