package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.TipoProduto;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Volume;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
public class EncomendaBean {
    @PersistenceContext
    private EntityManager em;

    // Criar uma encomenda
    public Encomenda create(String clienteUsername, int estado) {
        Encomenda encomenda = new Encomenda();
        encomenda.setClienteUsername(clienteUsername);
        encomenda.setEstado(estado);
        LocalDateTime now = LocalDateTime.now();
        Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        encomenda.setDataCriacao(date);
        em.persist(encomenda);
        return encomenda;
    }

    // Buscar todas as encomendas
    public List<Encomenda> findAll() {
        return em.createNamedQuery("getAllEncomendas", Encomenda.class).getResultList();
    }

    // Buscar encomenda por ID
    public Encomenda find(int id) {
        return em.find(Encomenda.class, id);
    }

    public void update(int id , int estado) {
        Encomenda encomenda = em.find(Encomenda.class, id);

        if (encomenda == null) {
            throw new IllegalArgumentException("Encomenda with ID " + id + " not found.");
        }
        encomenda.setEstado(estado);
        em.merge(encomenda);
    }

    public void delete(int id) {
        Encomenda encomenda = find(id);
        if (encomenda != null) {
            // Remover volumes associados antes de remover a encomenda
            for (Volume volume : encomenda.getVolumes()) {
                em.remove(volume); // Remover volumes
            }
            em.remove(encomenda); // Remover encomenda
        }
    }
}