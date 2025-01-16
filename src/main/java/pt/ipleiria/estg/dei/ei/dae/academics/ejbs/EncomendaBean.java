package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Volume;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
public class EncomendaBean {
    @PersistenceContext
    private EntityManager em;

    // Criar uma encomenda
    public Encomenda create(int cliente, Date dataCriacao, int estado) {
        Encomenda encomenda = new Encomenda(cliente, dataCriacao, estado);
        em.persist(encomenda);
        return encomenda;
    }

    // Buscar todas as encomendas
    public List<Encomenda> findAll() {
        return em.createQuery("SELECT e FROM Encomenda e", Encomenda.class).getResultList();
    }

    // Buscar encomenda por ID
    public Encomenda find(int id) {
        return em.find(Encomenda.class, id);
    }

    // Adicionar volumes a uma encomenda
    public void addVolumes(Encomenda encomenda, List<Volume> volumes) {
        encomenda.getVolumes().addAll(volumes);
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