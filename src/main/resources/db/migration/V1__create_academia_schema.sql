CREATE TABLE alunos(
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    data_nascimento DATE,
    sexo VARCHAR(1) CHECK (sexo IN('M','F')),
    telefone VARCHAR(30),
    celular VARCHAR(30),
    email VARCHAR(150),
    observacao TEXT,
    endereco VARCHAR(150),
    numero VARCHAR(20),
    complemento VARCHAR(100),
    bairro VARCHAR(100),
    estado VARCHAR(2),
    cep VARCHAR(20),
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


