package dev.reward.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.reward.domain.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select e from Event e where e.id = :id")
    Event findByIdPessimisticLock(@Param("id") Long id);
}
