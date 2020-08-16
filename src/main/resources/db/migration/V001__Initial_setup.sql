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

CREATE TABLE IF NOT EXISTS invoice
(
    id                 BIGSERIAL PRIMARY KEY,
    number             BIGINT         NOT NULL,
    date               DATE           NOT NULL,
    value              NUMERIC(19, 2) NOT NULL,
    taker_id           BIGINT         NOT NULL
        CONSTRAINT taker_id_fk
            REFERENCES company,
    provider_id        BIGINT         NOT NULL
        CONSTRAINT provider_id_fk
            REFERENCES company,
    created_date       TIMESTAMP      NOT NULL DEFAULT NOW(),
    last_modified_date TIMESTAMP      NOT NULL DEFAULT NOW()
)
