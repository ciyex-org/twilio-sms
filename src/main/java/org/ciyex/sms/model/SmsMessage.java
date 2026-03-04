package org.ciyex.sms.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sms_messages")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SmsMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "org_alias", nullable = false)
    private String orgAlias;

    @Column(nullable = false)
    @Builder.Default
    private String direction = "OUTBOUND";

    @Column(name = "to_number", nullable = false)
    private String toNumber;

    @Column(name = "from_number", nullable = false)
    private String fromNumber;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(nullable = false)
    @Builder.Default
    private String status = "QUEUED";

    @Column(name = "twilio_sid")
    private String twilioSid;

    @Column(name = "patient_id")
    private String patientId;

    @Column(name = "created_by")
    private String createdBy;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
