package org.ciyex.sms.repository;

import org.ciyex.sms.model.SmsMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SmsMessageRepository extends JpaRepository<SmsMessage, UUID> {

    Page<SmsMessage> findByOrgAlias(String orgAlias, Pageable pageable);

    Page<SmsMessage> findByOrgAliasAndPatientId(String orgAlias, String patientId, Pageable pageable);

    Optional<SmsMessage> findByTwilioSid(String twilioSid);

    Optional<SmsMessage> findByIdAndOrgAlias(UUID id, String orgAlias);
}
