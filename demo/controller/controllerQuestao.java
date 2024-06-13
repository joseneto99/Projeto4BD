package projetoescola.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetoescola.demo.modelo.Questao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@RestController
@RequestMapping("/questoes")
public class controllerQuestao {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping
    public List<Questao> getAllQuestoes() {
        return entityManager.createQuery("from Questao", Questao.class).getResultList();
    }

    @GetMapping("/{idQuestao}")
    public ResponseEntity<Questao> getQuestaoById(@PathVariable Long idQuestao) {
        Questao questao = entityManager.find(Questao.class, idQuestao);
        if (questao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(questao);
    }

    @PostMapping
    @Transactional
    public Questao createQuestao(@RequestBody Questao questao) {
        entityManager.persist(questao);
        return questao;
    }

    @PutMapping("/{idQuestao}")
    @Transactional
    public ResponseEntity<Questao> updateQuestao(@PathVariable Long idQuestao, @RequestBody Questao questaoDetails) {
        Questao questao = entityManager.find(Questao.class, idQuestao);
        if (questao == null) {
            return ResponseEntity.notFound().build();
        }
        questao.setNivel(questaoDetails.getNivel());
        questao.setTexto(questaoDetails.getTexto());
        questao.setGabarito(questaoDetails.getGabarito());
        questao.setComentarios(questaoDetails.getComentarios());
        questao.setAlternativas(questaoDetails.getAlternativas());
        entityManager.merge(questao);
        return ResponseEntity.ok(questao);
    }

    @DeleteMapping("/{idQuestao}")
    @Transactional
    public ResponseEntity<Void> deleteQuestao(@PathVariable Long idQuestao) {
        Questao questao = entityManager.find(Questao.class, idQuestao);
        if (questao == null) {
            return ResponseEntity.notFound().build();
        }
        entityManager.remove(questao);
        return ResponseEntity.ok().build();
    }
}
