package projetoescola.demo.modelo;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Mensalidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMensalidade;

    @Column(nullable = false)
    private Date dataVencimento;

    @Column(nullable = false)
    private Float valor;

    @Column(nullable = true)
    private Date dataPagamento;

    @Column(nullable = false)
    private Long fkAlunoIdUsuario;

    // Getters and Setters
    public Long getIdMensalidade() {
        return idMensalidade;
    }

    public void setIdMensalidade(Long idMensalidade) {
        this.idMensalidade = idMensalidade;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Long getFkAlunoIdUsuario() {
        return fkAlunoIdUsuario;
    }

    public void setFkAlunoIdUsuario(Long fkAlunoIdUsuario) {
        this.fkAlunoIdUsuario = fkAlunoIdUsuario;
    }
}
