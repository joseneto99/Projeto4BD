package projetoescola.demo.modelo;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDisciplina;

    @Column(nullable = false)
    private Integer qtdTurmas;

    @Column(nullable = false, length = 100)
    private String nome;

    // Getters and Setters
    public Long getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(Long idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public Integer getQtdTurmas() {
        return qtdTurmas;
    }

    public void setQtdTurmas(Integer qtdTurmas) {
        this.qtdTurmas = qtdTurmas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
