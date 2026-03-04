package org.ciyex.sms.repository;

import org.ciyex.sms.model.DeliveryEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeliveryEventRepository extends JpaRepository<DeliveryEvent, UUID> {

    List<DeliveryEvent> findByMessageIdOrderByCreatedAtDesc(UUID messageId);
}
