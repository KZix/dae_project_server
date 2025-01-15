package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.TipoProduto;

import java.util.List;

@Stateless
public class ProdutoBean {
    @PersistenceContext
    private EntityManager em;

    public Produto create(String nome, float preco, int tipoProdutoId) {
        // Find the TipoProduto entity by its ID
        TipoProduto tipoProduto = em.find(TipoProduto.class, tipoProdutoId);
        if (tipoProduto == null) {
            throw new IllegalArgumentException("TipoProduto with ID " + tipoProdutoId + " not found.");
        }

        // Create and persist the Produto
        Produto produto = new Produto(nome, preco, tipoProduto);
        em.persist(produto);
        return produto;
    }

    public List<Produto> findAll() {
        // remember, maps to: “SELECT s FROM Produto s ORDER BY s.name”
        return em.createNamedQuery("getAllProducts", Produto.class).getResultList();
    }

    public Produto find(int id) {
        return em.find(Produto.class, id);
    }

    public void update(int id, String nome, float preco, int tipoProdutoId) {
        Produto produto = find(id);
        if (produto != null) {
            TipoProduto tipoProduto = em.find(TipoProduto.class, tipoProdutoId);
            if (tipoProduto == null) {
                throw new IllegalArgumentException("TipoProduto with ID " + tipoProdutoId + " not found.");
            }

            produto.setNome(nome);
            produto.setPreco(preco);
            produto.setTipoProduto(tipoProduto);
            em.merge(produto);
        }
    }

    public void delete(int id) {
        Produto produto = find(id);
        if (produto != null) {
            em.remove(produto);
        }
    }
}
