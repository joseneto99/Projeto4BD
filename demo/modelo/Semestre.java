package projetoescola.demo.modelo;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Semestre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSemestre;

    @Column(nullable = false)
    private Boolean tempoSemestre;

    // Getters and Setters
    public Long getIdSemestre() {
        return idSemestre;
    }

    public void setIdSemestre(Long idSemestre) {
        this.idSemestre = idSemestre;
    }

    public Boolean getTempoSemestre() {
        return tempoSemestre;
    }

    public void setTempoSemestre(Boolean tempoSemestre) {
        this.tempoSemestre = tempoSemestre;
    }
}
