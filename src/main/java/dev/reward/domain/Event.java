package dev.reward.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import dev.reward.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Event extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Long quantity;

    // private Long limitAmount;

    // private Long remainAmount;

    public Event(String title, Long quantity) {
        this.title = title;
        this.quantity = quantity;
    }

    public void decrease(Long quantity) {
        if (this.quantity - quantity < 0) {
            throw new RuntimeException("선착순 이벤트가 마감 되었습니다.");
        }

        this.quantity = this.quantity - quantity;
    }

}
