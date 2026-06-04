INSERT INTO accounts (telegram_id, first_name, username, language_code, is_bot, is_premium)
VALUES (1310206331, 'Henrique', 'henriquebarrosx', 'pt-br', FALSE, FALSE);


INSERT INTO transactions (account_id, period, description, amount, transaction_type, is_settled)
VALUES 
    -- Período: Maio de 2026
    ((SELECT id FROM accounts WHERE telegram_id = 1310206331), '2026-05-01', 'Salário CLT', 5000.00, 'receita', TRUE),
    ((SELECT id FROM accounts WHERE telegram_id = 1310206331), '2026-05-01', 'Conta de Energia Equatorial', 250.50, 'despesa', TRUE),
    ((SELECT id FROM accounts WHERE telegram_id = 1310206331), '2026-05-01', 'Supermercado', 600.00, 'despesa', TRUE),
    ((SELECT id FROM accounts WHERE telegram_id = 1310206331), '2026-05-01', 'Assinatura Internet', 99.90, 'despesa', TRUE),
    ((SELECT id FROM accounts WHERE telegram_id = 1310206331), '2026-05-01', 'Freelance Angular', 1200.00, 'receita', FALSE), -- Ainda não transferido
    
    -- Período: Junho de 2026 (Mês Atual)
    ((SELECT id FROM accounts WHERE telegram_id = 1310206331), '2026-06-01', 'Salário CLT', 5000.00, 'receita', TRUE),
    ((SELECT id FROM accounts WHERE telegram_id = 1310206331), '2026-06-01', 'Aluguel Ap', 1200.00, 'despesa', TRUE),
    ((SELECT id FROM accounts WHERE telegram_id = 1310206331), '2026-06-01', 'Condomínio', 350.00, 'despesa', TRUE),
    ((SELECT id FROM accounts WHERE telegram_id = 1310206331), '2026-06-01', 'Conta de Energia prévia', 180.00, 'despesa', FALSE); -- Pendência / Não paga ainda