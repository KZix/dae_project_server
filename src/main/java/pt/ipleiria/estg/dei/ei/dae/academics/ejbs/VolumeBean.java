package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Produto;

import java.util.List;

@Stateless
public class VolumeBean {
    @PersistenceContext
    private EntityManager em;

    public Volume create(String descricao, List<Produto> produtos) {
        Volume volume = new Volume(descricao, produtos);
        em.persist(volume);
        return volume;
    }

    public Volume find(int id) {
        return em.find(Volume.class, id);
    }

    public void update(int id, String descricao, List<Produto> produtos) {
        Volume volume = find(id);
        if (volume != null) {
            volume.setDescricao(descricao);
            volume.setProdutos(produtos);
            em.merge(volume);
        }
    }

    public void delete(int id) {
        Volume volume = find(id);
        if (volume != null) {
            em.remove(volume);
        }
    }
}
