--Stored Procedure para Atualizar o Valor de uma Mensalidade
CREATE OR REPLACE PROCEDURE AtualizarValorMensalidade(
    p_data_pagamento INTEGER,
    p_novo_valor FLOAT
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE Mensalidade
    SET valor = p_novo_valor
    WHERE data_pagamento = p_data_pagamento;
END;
$$;
--CALL InserirLivro('Novo Livro', 1, 1);