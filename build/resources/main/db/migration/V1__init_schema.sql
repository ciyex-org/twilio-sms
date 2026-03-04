-- V1: Twilio SMS service schema

CREATE TABLE sms_messages (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    org_alias VARCHAR(100) NOT NULL,
    direction VARCHAR(10) NOT NULL DEFAULT 'OUTBOUND',
    to_number VARCHAR(30) NOT NULL,
    from_number VARCHAR(30) NOT NULL,
    body TEXT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'QUEUED',
    twilio_sid VARCHAR(50),
    patient_id VARCHAR(50),
    created_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_sms_org ON sms_messages(org_alias);
CREATE INDEX idx_sms_patient ON sms_messages(org_alias, patient_id);
CREATE INDEX idx_sms_twilio_sid ON sms_messages(twilio_sid);

CREATE TABLE delivery_events (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    message_id UUID NOT NULL REFERENCES sms_messages(id),
    status VARCHAR(20) NOT NULL,
    raw_payload JSONB,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_delivery_msg ON delivery_events(message_id);

-- RLS
ALTER TABLE sms_messages ENABLE ROW LEVEL SECURITY;
CREATE POLICY sms_org_isolation ON sms_messages
    USING (org_alias = current_setting('app.current_org', true)
           OR current_setting('app.current_org', true) = '__SYSTEM__');

ALTER TABLE delivery_events ENABLE ROW LEVEL SECURITY;
CREATE POLICY delivery_org_isolation ON delivery_events
    USING (message_id IN (
        SELECT id FROM sms_messages
        WHERE org_alias = current_setting('app.current_org', true)
           OR current_setting('app.current_org', true) = '__SYSTEM__'
    ));
