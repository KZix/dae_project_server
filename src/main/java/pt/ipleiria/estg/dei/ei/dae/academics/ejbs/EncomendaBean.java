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

    public Encomenda create(int cliente, Date dataCriacao, int estado, List<Volume> volumes) {
        // Criar a encomenda com cliente, dataCriacao e estado
        Encomenda encomenda = new Encomenda(cliente, dataCriacao, estado, volumes);

        // Associar volumes à encomenda
        for (Volume volume : volumes) {
            volume.setEncomenda(encomenda); // Associar encomenda a cada volume
        }

        // Persistir a encomenda e os volumes
        em.persist(encomenda);
        for (Volume volume : volumes) {
            em.persist(volume); // Persistir volumes individualmente
        }

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

            // Reassociar a encomenda a cada volume
            for (Volume volume : volumes) {
                volume.setEncomenda(encomenda); // Garantir que a encomenda está associada ao volume
            }

            em.merge(encomenda);
            for (Volume volume : volumes) {
                em.merge(volume); // Atualizar volumes individualmente
            }
        }
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