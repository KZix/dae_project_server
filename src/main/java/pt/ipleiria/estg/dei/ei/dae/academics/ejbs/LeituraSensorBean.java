package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.LeituraSensor;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.SensorAceleracao;

import java.util.Date;
import java.util.List;

@Stateless
public class LeituraSensorBean {
    @PersistenceContext
    private EntityManager em;

    public void registerReading(int sensorId, float valor) {
        Sensor sensor = em.find(Sensor.class, sensorId);
        if (sensor instanceof SensorAceleracao) {
            // Cast to SensorAceleracao
            SensorAceleracao sensorAceleracao = (SensorAceleracao) sensor;

            // Detect impact based on the provided value
            if (sensorAceleracao.detectarImpacto(valor)) {
                // If an impact is detected, update the impact count in the database
                sensorAceleracao.setImpactoCount(sensorAceleracao.getImpactoCount());
                // Persist changes if necessary
            }
        }
        // Persist the reading
        LeituraSensor leituraSensor = new LeituraSensor();
        leituraSensor.setValor(valor);
        leituraSensor.setSensor(sensor);
        leituraSensor.setTimestamp(new Date());
        em.persist(leituraSensor);
    }

    public List<LeituraSensor> listReadingsBySensor(int sensorId) {
        return em.createQuery(
                        "SELECT l FROM LeituraSensor l WHERE l.sensor.id = :sensorId", LeituraSensor.class)
                .setParameter("sensorId", sensorId)
                .getResultList();
    }
}
