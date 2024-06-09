--View para Listar Informações dos Livros e Seus Emprestadores
CREATE OR REPLACE VIEW LivroEmprestadores AS
SELECT 
    l.isbn,
    l.nome AS nome_livro,
    p.nome AS nome_professor,
    a.nome AS nome_aluno,
    e.data_emprestimo,
    e.data_devolucao,
    e.valor_multa
FROM Livro l
LEFT JOIN Emprestimo e ON l.isbn = e.fk_Livro_isbn
LEFT JOIN Professor p ON l.fk_Professor_id_usuario = p.id_usuario
LEFT JOIN Aluno a ON l.fk_Aluno_id_usuario = a.id_usuario;
