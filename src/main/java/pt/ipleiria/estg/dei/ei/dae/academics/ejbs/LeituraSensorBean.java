package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.LeituraSensor;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Sensor;

import java.util.Date;
import java.util.List;

@Stateless
public class LeituraSensorBean {
    @PersistenceContext
    private EntityManager em;

    public void registerReading(int sensorId, float valor) {
        Sensor sensor = em.find(Sensor.class, sensorId);
        if (sensor != null) {
            LeituraSensor leitura = new LeituraSensor(sensor, valor, new Date());
            em.persist(leitura);
            sensor.getLeituras().add(leitura);
            sensor.setUltimaLeitura(new Date());
            sensor.setValor(valor);
            em.merge(sensor);
        }
    }

    public List<LeituraSensor> listReadingsBySensor(int sensorId) {
        return em.createQuery(
                        "SELECT l FROM LeituraSensor l WHERE l.sensor.id = :sensorId", LeituraSensor.class)
                .setParameter("sensorId", sensorId)
                .getResultList();
    }
}
