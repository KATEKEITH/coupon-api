package dev.reward.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.reward.domain.Event;
import dev.reward.excpetion.NotFoundException;
import dev.reward.repository.EventRepository;

@Transactional
@Service
public class EventService {

    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional(readOnly = true)
    public Event findEntityById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException());
    }

    public void decrease(Long id, Long quentity) {
        Event evnet = eventRepository.findByIdPessimisticLock(id);
        if (evnet == null) {
            throw new NotFoundException();
        }
        evnet.decrease(quentity);
        eventRepository.saveAndFlush(evnet);
    }
}
