MERGE INTO company(id, trade_name, name, document_identifier, type, created_date, last_modified_date) values
(nextval('company_id_seq'), 'Mante, Mante and Mante', 'Mante, Mante and Mante SA', '93491176000174', 'TAKER', '2020-05-02 20:26:42.415491', '2020-05-02 20:26:42.415491'),
(nextval('company_id_seq'), 'Wisozk-Wisozk', 'Wisozk-Wisozk SA', '36128526000107', 'PROVIDER', '2020-05-02 20:26:42.415491', '2020-05-02 20:26:42.415491');
