CREATE TABLE IF NOT EXISTS cnab_record (
    id VARCHAR(30) NOT NULL PRIMARY KEY,
    type INT NOT NULL,
    date DATE NULL,
    value NUMERIC(10,2) NOT NULL,
    cpf VARCHAR NOT NULL,
    card_number VARCHAR NOT NULL,
    hour TIME NOT NULL,
    store_representative_name VARCHAR NOT NULL,
    store_name VARCHAR NOT NULL
)


