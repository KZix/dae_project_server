package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.SensorAceleracao;

@Stateless
public class SensorAceleracaoBean extends SensorBean {
    @PersistenceContext
    private EntityManager em;
    public boolean detectarImpacto(int sensorId, float aceleracao) {
        SensorAceleracao sensor = em.find(SensorAceleracao.class, sensorId);
        if (sensor != null) {
            return sensor.detectarImpacto(aceleracao);
        }
        return false;
    }

    public int obterImpactos(int sensorId) {
        SensorAceleracao sensor = em.find(SensorAceleracao.class, sensorId);
        if (sensor != null) {
            return sensor.getImpactoCount();
        }
        return 0;
    }
}

