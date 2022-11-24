package dev.reward.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.reward.domain.User;
import dev.reward.interfaces.dto.RewardForm;
import dev.reward.interfaces.dto.DetailResponse;
import dev.reward.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public DetailResponse findEntityByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return DetailResponse.of(user);
    }

    public User rigister(RewardForm rewardForm) {
        User user = userRepository.findByEmail(rewardForm.getEmail());
        if (user == null) {
            User newUser = new User(rewardForm.getEmail(), 0L);
            User createdUser = userRepository.save(newUser);
            return createdUser;
        } else {
            return user;
        }
    }

    public void update(String email, Long point) {
        User user = userRepository.findByEmail(email);
        user.update(point);
        userRepository.save(user);
    }

}
