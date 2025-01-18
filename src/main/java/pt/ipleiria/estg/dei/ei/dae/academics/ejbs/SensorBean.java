package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Stateless
public class SensorBean {
    @PersistenceContext
    private EntityManager em;



    public Sensor createGenericSensor(String name, int volume, boolean estado) {
        Sensor sensor = new Sensor();
        sensor.setName(name);
        sensor.setVolume(volume);
        sensor.setEstado(estado);
        sensor.setUltimaLeitura(new Date());
        em.persist(sensor);
        return sensor;
    }

    public SensorPosicao createSensorPosicao(String name, int volume, boolean estado, float posicao) {
        SensorPosicao sensorPosicao = new SensorPosicao();
        sensorPosicao.setName(name);
        sensorPosicao.setVolume(volume);
        sensorPosicao.setEstado(estado);
        sensorPosicao.setUltimaLeitura(new Date());
        sensorPosicao.setPosicao(posicao);
        em.persist(sensorPosicao);
        return sensorPosicao;
    }

    public SensorAceleracao createSensorAceleracao(String name, int volume, boolean estado, int impactCount) {
        SensorAceleracao sensorAceleracao = new SensorAceleracao();
        sensorAceleracao.setName(name);
        sensorAceleracao.setVolume(volume);
        sensorAceleracao.setEstado(estado);
        sensorAceleracao.setUltimaLeitura(new Date());
        sensorAceleracao.setImpactoCount(impactCount);
        em.persist(sensorAceleracao);
        return sensorAceleracao;
    }

    public SensorTemperatura createSensorTemperatura(String name, int volume, boolean estado, float threshold) {
        SensorTemperatura sensorTemperatura = new SensorTemperatura();
        sensorTemperatura.setName(name);
        sensorTemperatura.setVolume(volume);
        sensorTemperatura.setEstado(estado);
        sensorTemperatura.setUltimaLeitura(new Date());
        sensorTemperatura.setThreshold(threshold);
        em.persist(sensorTemperatura);
        return sensorTemperatura;
    }

    @EJB
    private LeituraSensorBean leituraSensorBean;

    public void addSensorReading(int sensorId, float valor) {
        leituraSensorBean.registerReading(sensorId, valor);
    }

    public void deleteSensor(int id) {
        Sensor sensor = findSensor(id);
        if (sensor != null) {
            em.remove(sensor);
        } else {
            throw new IllegalArgumentException("Sensor with ID " + id + " not found.");
        }
    }

    public Sensor findSensor(int id) {
        return em.find(Sensor.class, id);
    }

    public void updateSensor(int id, int volume, boolean estado) {
        Sensor sensor = findSensor(id);
        if (sensor != null) {
            sensor.setVolume(volume);
            sensor.setEstado(estado);
            sensor.setUltimaLeitura(new Date()); // Update the last read time
            em.merge(sensor);
        } else {
            throw new IllegalArgumentException("Sensor with ID " + id + " not found.");
        }
    }

    public List<Sensor> getAllSensors() {
        return em.createQuery("SELECT s FROM Sensor s", Sensor.class).getResultList();
    }

    public boolean detectImpact(int id, float acceleration) {
        Sensor sensor = findSensor(id);
        if (sensor instanceof SensorAceleracao) {
            SensorAceleracao sensorAceleracao = (SensorAceleracao) sensor;
            return sensorAceleracao.detectarImpacto(acceleration);
        } else {
            throw new IllegalArgumentException("Sensor with ID " + id + " is not a SensorAceleracao.");
        }
    }

    public int getImpactCount(int id) {
        Sensor sensor = findSensor(id);
        if (sensor instanceof SensorAceleracao) {
            SensorAceleracao sensorAceleracao = (SensorAceleracao) sensor;
            return sensorAceleracao.getImpactoCount();
        } else {
            throw new IllegalArgumentException("Sensor with ID " + id + " is not a SensorAceleracao.");
        }
    }
    public boolean addTemperatureReading(int sensorId, float temperature) {
        // Find the sensor by ID
        Sensor sensor = findSensor(sensorId);
        if (sensor instanceof SensorTemperatura) {
            SensorTemperatura sensorTemperatura = (SensorTemperatura) sensor;

            // Create a new LeituraSensor entry
            LeituraSensor leituraSensor = new LeituraSensor();
            leituraSensor.setTimestamp(new Date());
            leituraSensor.setValor(temperature);  // Store the temperature value
            leituraSensor.setSensor(sensorTemperatura);  // Link to the sensor

            // Persist the new reading to the database
            em.persist(leituraSensor);

            // Update the sensor's last reading time
            sensorTemperatura.setUltimaLeitura(new Date());

            // Merge to update the sensor entity
            em.merge(sensorTemperatura);

            return true;
        }
        return false;
    }


    public boolean addPositionReading(int sensorId, float position) {
        // Find the sensor by ID
        Sensor sensor = findSensor(sensorId);
        if (sensor instanceof SensorPosicao) {
            SensorPosicao sensorPosicao = (SensorPosicao) sensor;

            // Create a new LeituraSensor entry for position reading
            LeituraSensor leituraSensor = new LeituraSensor();
            leituraSensor.setTimestamp(new Date());
            leituraSensor.setValor(position);  // Store the position value
            leituraSensor.setSensor(sensorPosicao);  // Link to the sensor

            // Persist the new reading to the database
            em.persist(leituraSensor);

            // Update the sensor's last reading time
            sensorPosicao.setUltimaLeitura(new Date());

            // Merge to update the sensor entity
            em.merge(sensorPosicao);

            return true;
        }
        return false;
    }



    public void updateSensorTemperaturaThreshold(int id, float threshold) {
        Sensor sensor = em.find(Sensor.class, id);
        if (sensor instanceof SensorTemperatura) {
            SensorTemperatura sensorTemperatura = (SensorTemperatura) sensor;
            sensorTemperatura.setThreshold(threshold);
            em.merge(sensorTemperatura); // Update the entity
        }
    }

    // Method to register a temperature reading and check if it's below the threshold
    public boolean registerTemperatureReading(int sensorId, float temperature) {
        Sensor sensor = findSensor(sensorId);
        if (sensor instanceof SensorTemperatura) {
            SensorTemperatura sensorTemperatura = (SensorTemperatura) sensor;
            return sensorTemperatura.checkTemperatureBelowThreshold(temperature);
        }
        return false; // Return false if the sensor is not of type SensorTemperatura
    }

    // Method to get the number of times the temperature was below the threshold
    public int getBelowThresholdCount(int sensorId) {
        Sensor sensor = findSensor(sensorId);
        if (sensor instanceof SensorTemperatura) {
            SensorTemperatura sensorTemperatura = (SensorTemperatura) sensor;
            return sensorTemperatura.getBelowThresholdCount();
        }
        return 0; // Return 0 if the sensor is not of type SensorTemperatura
    }


}


