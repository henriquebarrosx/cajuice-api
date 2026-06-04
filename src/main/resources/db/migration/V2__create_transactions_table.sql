CREATE TABLE IF NOT EXISTS transactions (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    period DATE NOT NULL,
    description VARCHAR(256) NOT NULL,
    amount NUMERIC(12, 2) NOT NULL,
    transaction_type VARCHAR(10) NOT NULL,
    is_settled BOOLEAN NOT NULL DEFAULT FALSE,
	deleted_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_account
        FOREIGN KEY (account_id)
        REFERENCES accounts(id)
        ON DELETE CASCADE,
        
    CONSTRAINT chk_transaction_type
        CHECK (transaction_type IN ('RECEITA', 'DESPESA'))
);


CREATE INDEX idx_transactions_account_period ON transactions (account_id, period);