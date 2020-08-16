CREATE TABLE IF NOT EXISTS company
(
    id                  BIGSERIAL PRIMARY KEY,
    trade_name          VARCHAR(255),
    name                VARCHAR(255) NOT NULL,
    document_identifier VARCHAR(255) NOT NULL,
    type                VARCHAR(255) NOT NULL,
    created_date        TIMESTAMP    NOT NULL DEFAULT NOW(),
    last_modified_date  TIMESTAMP    NOT NULL DEFAULT NOW()
);