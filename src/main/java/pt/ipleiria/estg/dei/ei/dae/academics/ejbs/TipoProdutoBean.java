package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.TipoProduto;

import java.util.List;

@Stateless
public class TipoProdutoBean {
    @PersistenceContext
    private EntityManager em;

    public List<TipoProduto> findAll() {
        // remember, maps to: “SELECT s FROM Produto s ORDER BY s.name”
        return em.createNamedQuery("getAllTipoProdutos", TipoProduto.class).getResultList();
    }

    public TipoProduto create(String nome) {
        TipoProduto tipoProduto = new TipoProduto(nome);
        em.persist(tipoProduto);
        return tipoProduto;
    }

    public TipoProduto find(int id) {
        return em.find(TipoProduto.class, id);
    }

    public void update(int id, String nome) {
        TipoProduto tipoProduto = find(id);
        if (tipoProduto != null) {
            tipoProduto.setNome(nome);
            em.merge(tipoProduto);
        }
    }

    public void delete(int id) {
        TipoProduto tipoProduto = find(id);
        if (tipoProduto != null) {
            em.remove(tipoProduto);
        }
    }
}
