package dev.reward.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    private Long point;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reward> rewards = new ArrayList<>();

    @Builder
    public User(String email, Long point) {
        this.email = email;
        this.point = point;
    }

    public void update(Long point) {
        this.point = this.point + point;
    }

    public void addReward(Reward reward) {
        this.rewards.add(reward);
    }

}
