-- Dropar triggers se já existirem
DROP TRIGGER IF EXISTS aumentar_mensalidade_trigger ON Mensalidade;
DROP TRIGGER IF EXISTS incrementar_isbn_trigger ON Livro;
DROP TRIGGER IF EXISTS excluir_professor_trigger ON Professor;
DROP TRIGGER IF EXISTS evitar_exclusao_livro_essencial_trigger ON Livro;

-- Dropar tabelas se já existirem
DROP TABLE IF EXISTS Livro;
DROP TABLE IF EXISTS Mensalidade;
DROP TABLE IF EXISTS Aluno;
DROP TABLE IF EXISTS Professor;
DROP TABLE IF EXISTS Questao;

-- Definição das tabelas
CREATE TABLE Livro (
    isbn SERIAL PRIMARY KEY,
    nome VARCHAR,
    fk_Professor_id_usuario INTEGER,
    fk_Aluno_id_usuario INTEGER
);

CREATE TABLE Mensalidade (
    data_vencimento DATE,
    valor FLOAT,
    data_pagamento SERIAL PRIMARY KEY,
    fk_Aluno_id_usuario INTEGER
);

CREATE TABLE Aluno (
    ra VARCHAR,
    tipo_plano CHAR,
    id_usuario SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    numero VARCHAR(100),
    email VARCHAR(100),
    idade INTEGER,
    sexo CHAR,
    cpf VARCHAR
);

CREATE TABLE Professor (
    salario FLOAT,
    cargo VARCHAR(50),
    id_usuario SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    numero VARCHAR(100),
    email VARCHAR(100),
    idade INTEGER,
    sexo CHAR,
    cpf VARCHAR
);

CREATE TABLE Questao (
    id_questao SERIAL PRIMARY KEY,
    nivel VARCHAR,
    texto VARCHAR,
    gabarito VARCHAR,
    comentarios VARCHAR,
    alternativas VARCHAR
);

-- Inserção de dados nas tabelas
INSERT INTO Livro (isbn, nome, fk_Professor_id_usuario, fk_Aluno_id_usuario)
VALUES (1, 'Livro 1', 1, 1),
       (2, 'Livro 2', 2, 2)
ON CONFLICT DO NOTHING;

INSERT INTO Mensalidade (data_vencimento, valor, data_pagamento, fk_Aluno_id_usuario)
VALUES ('2024-04-30', 50.00, 1, 1),
       ('2024-05-30', 50.00, 2, 2)
ON CONFLICT DO NOTHING;

INSERT INTO Aluno (ra, tipo_plano, id_usuario, nome, numero, email, idade, sexo, cpf)
VALUES ('123456', 'A', 1, 'João', '99999999', 'joao@example.com', 25, 'M', '123.456.789-01'),
       ('789012', 'B', 2, 'Maria', '88888888', 'maria@example.com', 30, 'F', '987.654.321-09')
ON CONFLICT DO NOTHING;

INSERT INTO Professor (salario, cargo, id_usuario, nome, numero, email, idade, sexo, cpf)
VALUES (5000.00, 'Professor', 1, 'Carlos', '77777777', 'carlos@example.com', 35, 'M', '456.789.012-34'),
       (6000.00, 'Coordenador', 2, 'Ana', '66666666', 'ana@example.com', 40, 'F', '654.321.098-76')
ON CONFLICT DO NOTHING;

INSERT INTO Questao (nivel, texto, gabarito, comentarios, alternativas)
VALUES ('Fácil', 'Qual é a capital do Brasil?', 'Brasília', 'Comentário sobre a questão', '{"a": "Rio de Janeiro", "b": "São Paulo", "c": "Brasília", "d": "Belo Horizonte"}'),
       ('Médio', 'Quem pintou Mona Lisa?', 'Leonardo da Vinci', 'Comentário sobre a questão', '{"a": "Pablo Picasso", "b": "Vincent van Gogh", "c": "Leonardo da Vinci", "d": "Michelangelo"}')
ON CONFLICT DO NOTHING;

-- Triggers
-- Trigger para aumentar o valor da mensalidade em 50% após um mês da data de vencimento
CREATE OR REPLACE FUNCTION aumentar_mensalidade()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.data_vencimento <= CURRENT_DATE THEN
        NEW.valor := NEW.valor * 1.5;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER aumentar_mensalidade_trigger
BEFORE INSERT OR UPDATE ON Mensalidade
FOR EACH ROW EXECUTE FUNCTION aumentar_mensalidade();

-- Trigger para aumentar automaticamente o ISBN (código do livro) ao adicionar um novo livro
CREATE OR REPLACE FUNCTION incrementar_isbn()
RETURNS TRIGGER AS $$
DECLARE
    novo_isbn INTEGER;
BEGIN
    SELECT MAX(isbn) + 1 INTO novo_isbn FROM Livro;
    IF novo_isbn IS NULL THEN
        novo_isbn := 1;
    END IF;
    NEW.isbn := novo_isbn;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER incrementar_isbn_trigger
BEFORE INSERT ON Livro
FOR EACH ROW EXECUTE FUNCTION incrementar_isbn();

-- Trigger para excluir automaticamente um professor da tabela de professores se seu cargo for alterado para algo além de "Professor"
CREATE OR REPLACE FUNCTION excluir_professor()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.cargo != 'Professor' THEN
        DELETE FROM Professor WHERE id_usuario = NEW.id_usuario;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER excluir_professor_trigger
BEFORE UPDATE ON Professor
FOR EACH ROW EXECUTE FUNCTION excluir_professor();

-- Trigger para evitar a exclusão de livros essenciais na tabela Livro
CREATE OR REPLACE FUNCTION evitar_exclusao_livro_essencial()
RETURNS TRIGGER AS $$
BEGIN
    IF OLD.isbn IN (SELECT isbn FROM Livro WHERE fk_Professor_id_usuario IS NOT NULL OR fk_Aluno_id_usuario IS NOT NULL) THEN
        RAISE EXCEPTION 'Este livro não pode ser excluído pois está associado a um professor ou aluno.';
    END IF;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER evitar_exclusao_livro_essencial_trigger
BEFORE DELETE ON Livro
FOR EACH ROW EXECUTE FUNCTION evitar_exclusao_livro_essencial();