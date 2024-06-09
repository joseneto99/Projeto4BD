--Stored Procedure para Inserir um Novo Livro
CREATE OR REPLACE PROCEDURE InserirLivro(
    p_nome VARCHAR,
    p_fk_Professor_id_usuario INTEGER,
    p_fk_Aluno_id_usuario INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO Livro (nome, fk_Professor_id_usuario, fk_Aluno_id_usuario)
    VALUES (p_nome, p_fk_Professor_id_usuario, p_fk_Aluno_id_usuario);
END;
$$;
--CALL AtualizarValorMensalidade(1, 70.00);