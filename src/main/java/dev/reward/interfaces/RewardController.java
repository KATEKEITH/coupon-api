package dev.reward.interfaces;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dev.reward.domain.Event;
import dev.reward.domain.Reward;
import dev.reward.domain.User;
import dev.reward.interfaces.dto.RewardForm;
import dev.reward.interfaces.dto.RewardResultRequest;
import dev.reward.interfaces.dto.RewardResultResponse;
import dev.reward.interfaces.dto.DetailResponse;
import dev.reward.service.EventService;
import dev.reward.service.RewardService;
import dev.reward.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RewardController {

    private final UserService userService;

    private final EventService eventService;

    private final RewardService rewardService;

    Long point = 100L;

    @GetMapping("/reward")
    public String home(Model model) {
        log.info("진입");
        model.addAttribute("rewardForm", new RewardForm());
        return "reward";
    }

    @GetMapping("/reward/detail/{email}")
    public String detail(@PathVariable("email") String email, Model model) {
        DetailResponse user = userService.findEntityByEmail(email);
        model.addAttribute("user", user);
        return "detail";
    }

    @GetMapping("/reward/search")
    public String search(Model model) {
        model.addAttribute("RewardResultRequest", new RewardResultRequest());
        return "search";
    }

    @PostMapping("/reward/result")
    public String read(Model model,
            @Valid @ModelAttribute("RewardResultRequest") RewardResultRequest rewardResultRequest,
            BindingResult result) {
        List<RewardResultResponse> rewards = rewardService.search(rewardResultRequest);
        model.addAttribute("rewards", rewards);
        return "searchResult";
    }

    @PostMapping("/reward/{eventId:[\\d]+}")
    public String create(@PathVariable("eventId") Long eventId,
            @Valid @ModelAttribute("rewardForm") RewardForm rewardForm,
            BindingResult result) {

        // 이메일 입력을 안했거나
        if (result.hasErrors()) {
            return "reward";
        }

        // 이벤트가 종료되었거나
        Event event = eventService.findEntityById(eventId);
        if (event.getQuantity() == 0) {
            return "error";
        }

        User user = userService.rigister(rewardForm);
        Long rewardId = rewardService.register(user, rewardForm);
        if (rewardId != null) {
            eventService.decrease(eventId, 1L);
        }

        LocalDate today = LocalDateTime.now().toLocalDate();
        Period diff = Period.between(event.getCreatedDate().toLocalDate(), today);
        if (Math.floorMod(diff.getDays(), 10) == 2) {
            if (rewardService.checkDays(user.getId(), 2)) {
                point += 300L;
            }
        } else if (Math.floorMod(diff.getDays(), 10) == 4) {
            if (rewardService.checkDays(user.getId(), 4)) {
                point += 500L;
            }
        } else if (Math.floorMod(diff.getDays(), 10) == 9) {
            if (rewardService.checkDays(user.getId(), 9)) {
                point += 1000L;
            }
        }

        userService.update(rewardForm.getEmail(), point);
        return "success";
    }

}
