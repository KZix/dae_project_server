package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Volume;

import java.util.Date;
import java.util.List;

@Stateless
public class EncomendaBean {
    @PersistenceContext
    private EntityManager em;

    public Encomenda create(Date dataCriacao, List<Volume> volumes) {
        Encomenda encomenda = new Encomenda(dataCriacao, volumes);
        em.persist(encomenda);
        return encomenda;
    }

    public Encomenda find(int id) {
        return em.find(Encomenda.class, id);
    }

    public void update(int id, Date dataCriacao, List<Volume> volumes) {
        Encomenda encomenda = find(id);
        if (encomenda != null) {
            encomenda.setDataCriacao(dataCriacao);
            encomenda.setVolumes(volumes);
            em.merge(encomenda);
        }
    }

    public void delete(int id) {
        Encomenda encomenda = find(id);
        if (encomenda != null) {
            em.remove(encomenda);
        }
    }
}
