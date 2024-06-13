package projetoescola.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetoescola.demo.modelo.Mensalidade;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@RestController
@RequestMapping("/mensalidades")
public class ControllerMensalidade {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping
    public List<Mensalidade> getAllMensalidades() {
        return entityManager.createQuery("from Mensalidade", Mensalidade.class).getResultList();
    }

    @GetMapping("/{idMensalidade}")
    public ResponseEntity<Mensalidade> getMensalidadeById(@PathVariable Long idMensalidade) {
        Mensalidade mensalidade = entityManager.find(Mensalidade.class, idMensalidade);
        if (mensalidade == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mensalidade);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Mensalidade> createMensalidade(@RequestBody Mensalidade mensalidade) {
        try {
            entityManager.persist(mensalidade);
            return ResponseEntity.ok(mensalidade);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{idMensalidade}")
    @Transactional
    public ResponseEntity<Mensalidade> updateMensalidade(@PathVariable Long idMensalidade, @RequestBody Mensalidade mensalidadeDetails) {
        try {
            Mensalidade mensalidade = entityManager.find(Mensalidade.class, idMensalidade);
            if (mensalidade == null) {
                return ResponseEntity.notFound().build();
            }
            mensalidade.setDataVencimento(mensalidadeDetails.getDataVencimento());
            mensalidade.setValor(mensalidadeDetails.getValor());
            mensalidade.setDataPagamento(mensalidadeDetails.getDataPagamento());
            mensalidade.setFkAlunoIdUsuario(mensalidadeDetails.getFkAlunoIdUsuario());
            entityManager.merge(mensalidade);
            return ResponseEntity.ok(mensalidade);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{idMensalidade}")
    @Transactional
    public ResponseEntity<Void> deleteMensalidade(@PathVariable Long idMensalidade) {
        try {
            Mensalidade mensalidade = entityManager.find(Mensalidade.class, idMensalidade);
            if (mensalidade == null) {
                return ResponseEntity.notFound().build();
            }
            entityManager.remove(mensalidade);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
