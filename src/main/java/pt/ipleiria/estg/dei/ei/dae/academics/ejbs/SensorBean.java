package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Sensor;

import java.util.List;

@Stateless
public class SensorBean {
    @PersistenceContext
    private EntityManager em;

    public int create(String name, int volume, boolean estado, float valor) {
        Sensor sensor = new Sensor(); // Or instantiate specific subclass
        sensor.setName(name);
        sensor.setVolume(volume);
        sensor.setEstado(estado);
        sensor.setValor(valor);
        em.persist(sensor);
        return sensor.getId(); // Return the ID of the created sensor
    }

    @EJB
    private LeituraSensorBean leituraSensorBean;

    public void addSensorReading(int sensorId, float valor) {
        leituraSensorBean.registerReading(sensorId, valor);
    }

}


