package dev.reward.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import dev.reward.domain.Event;
import dev.reward.repository.EventRepository;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
public class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    public void before() {
        Event event = new Event("선착순 이벤트", 10L);

        eventRepository.saveAndFlush(event);
    }

    @AfterEach
    public void after() {
        eventRepository.deleteAll();
    }

    @Test
    public void stock_decrease() {
        Event event = new Event("선착순 이벤트", 10L);
        eventRepository.saveAndFlush(event);

        eventService.decrease(1L, 1L);
        Event events = eventRepository.findById(1L).orElseThrow();
        assertEquals(9, events.getQuantity());
    }

    @Test
    public void 동시에_10개_요청() throws InterruptedException {
        int threadCount = 10;

        Event event = new Event("선착순 이벤트", 10L);
        eventRepository.saveAndFlush(event);

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    eventService.decrease(1L, 1L);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        Event evnet = eventRepository.findById(1L).orElseThrow();
        assertEquals(0, evnet.getQuantity());
    }

}
