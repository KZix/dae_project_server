package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.SensorAceleracao;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.SensorPosicao;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.SensorTemperatura;

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

    public SensorPosicao createSensorPosicao(String name, int volume, boolean estado, float intervalo) {
        SensorPosicao sensorPosicao = new SensorPosicao();
        sensorPosicao.setName(name);
        sensorPosicao.setVolume(volume);
        sensorPosicao.setEstado(estado);
        sensorPosicao.setUltimaLeitura(new Date());
        sensorPosicao.setIntervalo(intervalo);
        em.persist(sensorPosicao);
        return sensorPosicao;
    }

    public SensorAceleracao createSensorAceleracao(String name, int volume, boolean estado, int impactCount) {
        SensorAceleracao sensorAceleracao = new SensorAceleracao();
        sensorAceleracao.setName(name);
        sensorAceleracao.setVolume(volume);
        sensorAceleracao.setEstado(estado);
        sensorAceleracao.setUltimaLeitura(new Date());
        sensorAceleracao.setImpactCount(impactCount);
        em.persist(sensorAceleracao);
        return sensorAceleracao;
    }

    public SensorTemperatura createSensorTemperatura(String name, int volume, boolean estado, float intervalo) {
        SensorTemperatura sensorTemperatura = new SensorTemperatura();
        sensorTemperatura.setName(name);
        sensorTemperatura.setVolume(volume);
        sensorTemperatura.setEstado(estado);
        sensorTemperatura.setUltimaLeitura(new Date());
        sensorTemperatura.setIntervalo(intervalo);
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

}


