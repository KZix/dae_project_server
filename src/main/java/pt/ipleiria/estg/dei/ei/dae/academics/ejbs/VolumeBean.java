package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityNotFoundException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Stateless
public class VolumeBean {
    @PersistenceContext
    private EntityManager em;

    @EJB
    private EncomendaBean encomendaBean;
    @EJB
    private SensorBean sensorBean;

    public Volume create(String descricao, int danificada, int encomenda_id)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        // Validar a encomenda
        var encomenda = encomendaBean.find(encomenda_id);
        if (encomenda == null) {
            throw new MyEntityNotFoundException("Encomenda nao encontrada");
        }

        // Criar o volume
        try {
            var volume = new Volume(descricao, danificada, encomenda);
            encomenda.addVolume(volume);
            em.persist(volume);
            em.flush();
            return volume;
        }catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public Volume find(int id) {
        return em.find(Volume.class, id);
    }

    public void update(int id, String descricao, List<Integer> produtoIds) {
        List<Produto> produtos = new LinkedList<>();
        Volume volume = find(id);
        if (volume != null) {
            volume.setDescricao(descricao);
            for (Integer produtoId : produtoIds) {
                Produto produto = em.find(Produto.class, produtoId);
                if (produto == null) {
                    throw new IllegalArgumentException("Produto with ID " + produtoId + " not found.");
                }
                produtos.add(produto);
            }
            volume.setProdutos(produtos);
            em.merge(volume);
        }
    }

    public void addProdutosToVolume(List<Integer> produtoIds, int idVolume) {
        Volume volume = em.find(Volume.class, idVolume);
        if (volume == null) {
            throw new IllegalArgumentException("Volume com o id " + idVolume + " nao encontrado.");
        }
        for (Integer produtoId : produtoIds) {
            Produto produto = em.find(Produto.class, produtoId);
            if (produto == null) {
                throw new IllegalArgumentException("Produto with ID " + produtoId + " not found.");
            }
            volume.addProduto(produto);
            produto.addVolume(volume);
            em.merge(produto);
        }

        em.merge(volume);


    }

    public Volume findWithProducts(int idVolume) {
        var volume = this.find(idVolume);
        Hibernate.initialize(volume.getProdutos());
        return volume;
    }

    public void delete(int id) {
        Volume volume = find(id);
        if (volume != null) {
            // Desassociar todos os produtos do volume
            for (Produto produto : new ArrayList<>(volume.getProdutos())) {
                produto.getVolumes().remove(volume); // Remover o volume da lista de produtos
                volume.getProdutos().remove(produto); // Remover o produto da lista do volume
            }
            // Remover o volume
            em.remove(volume);
        }
    }
    public void removeProdutosFromVolume(List<Integer> produtoIds, int idVolume) {
        // Retrieve the Volume object
        Volume volume = em.find(Volume.class, idVolume);
        if (volume == null) {
            throw new IllegalArgumentException("Volume com o id " + idVolume + " nao encontrado.");
        }

        // Loop through the provided produtoIds
        for (Integer produtoId : produtoIds) {
            Produto produto = em.find(Produto.class, produtoId);
            if (produto == null) {
                throw new IllegalArgumentException("Produto com o ID " + produtoId + " nao encontrado.");
            }

            // Remove associations between the Volume and Produto
            if (volume.getProdutos().contains(produto)) {
                volume.getProdutos().remove(produto);
                produto.getVolumes().remove(volume);
                em.merge(produto); // Persist changes to Produto
            } else {
                System.err.println("Produto with ID " + produtoId + " is not associated with Volume " + idVolume);
            }
        }

        // Persist changes to Volume
        em.merge(volume);
    }

    public void update(int id,String descricao, int danificada, int encomenda_id) {
        Volume volume = em.find(Volume.class, id);
        if (volume == null) {
            System.err.println("ERROR_VOLUME_NOT_FOUND: " + id);
            return;
        }
        em.lock(volume, LockModeType.OPTIMISTIC);
        volume.setDescricao(descricao);
        volume.setDanificada(danificada);
        if (volume.getEncomenda().getId() != encomenda_id) {
            Encomenda encomenda = em.find(Encomenda.class, encomenda_id);
            if (encomenda == null) {
                System.err.println("ERROR_ENCOMENDA_NOT_FOUND: " + encomenda_id);
                return;
            }
            volume.setEncomenda(encomenda);
        }
        em.merge(volume);
    }

    public void assignSensor(int volumeId, int sensorId) {
        Volume volume = find(volumeId);
        Sensor sensor = sensorBean.findSensor(sensorId);
        if (volume == null || sensor == null) {
            throw new IllegalArgumentException("Volume or Sensor not found.");
        }
        volume.setAssignedSensor(sensor);
        em.merge(volume);
    }


    public List<Volume> findAll() {
        return em.createNamedQuery("getAllVolumes", Volume.class).getResultList();
    }
}