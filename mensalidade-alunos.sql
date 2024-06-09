--View para Listar Informações de Mensalidades dos Alunos
CREATE OR REPLACE VIEW MensalidadesAlunos AS
SELECT 
    a.id_usuario,
    a.nome AS nome_aluno,
    a.tipo_plano,
    m.data_vencimento,
    m.valor,
    m.data_pagamento
FROM Aluno a
LEFT JOIN Mensalidade m ON a.id_usuario = m.fk_Aluno_id_usuario;