package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.SensorTemperatura;

@Stateless
public class SensorTemperaturaBean extends SensorBean {
    @PersistenceContext
    private EntityManager em;
    public void setTemperatureInterval(int sensorId, float intervalo) {

        SensorTemperatura sensor = em.find(SensorTemperatura.class, sensorId);
        if (sensor != null) {
            sensor.setIntervalo(intervalo);
            em.merge(sensor);
        }
    }
}

