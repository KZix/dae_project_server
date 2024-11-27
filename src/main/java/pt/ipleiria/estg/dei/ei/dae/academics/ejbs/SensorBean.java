package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Sensor;

import java.util.List;

@Stateless
public class SensorBean {
    @PersistenceContext
    private EntityManager em;

    public List<Sensor> getAllSensors() {
        return em.createQuery("SELECT s FROM Sensor s", Sensor.class).getResultList();
    }

    public Sensor findSensor(int id) {
        return em.find(Sensor.class, id);
    }

    // Other generic methods (add, update, delete)
}

