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
            // Create a new reading for the sensor
            LeituraSensor leitura = new LeituraSensor();
            leitura.setSensor(sensor);
            leitura.setValor(valor);
            leitura.setTimestamp(new Date());
            em.persist(leitura);

            // Update the sensor's last reading details
            sensor.setUltimaLeitura(leitura.getTimestamp());
            sensor.setValor(valor);
            em.merge(sensor);
        } else {
            throw new IllegalArgumentException("Sensor with ID " + sensorId + " not found");
        }
    }

    public List<LeituraSensor> listReadingsBySensor(int sensorId) {
        return em.createQuery(
                        "SELECT l FROM LeituraSensor l WHERE l.sensor.id = :sensorId", LeituraSensor.class)
                .setParameter("sensorId", sensorId)
                .getResultList();
    }
}
