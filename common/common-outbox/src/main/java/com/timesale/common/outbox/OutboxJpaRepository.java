package com.timesale.common.outbox;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxJpaRepository extends JpaRepository<Outbox, Long> {

    List<Outbox> findTop100ByPublishedFalseOrderByIdAsc();
}
