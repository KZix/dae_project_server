package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.SensorPosicao;

@Stateless
public class SensorPosicaoBean extends SensorBean {
    @PersistenceContext
    private EntityManager em;
    public void definirIntervalo(int sensorId, float intervalo) {
        SensorPosicao sensor = em.find(SensorPosicao.class, sensorId);
        if (sensor != null) {
            sensor.setIntervalo(intervalo);
            em.merge(sensor);
        }
    }

    public String obterPosicaoAtual(int sensorId) {
        SensorPosicao sensor = em.find(SensorPosicao.class, sensorId);
        if (sensor != null) {
            return sensor.obterPosicaoAtual();
        }
        return "No data available.";
    }
}

