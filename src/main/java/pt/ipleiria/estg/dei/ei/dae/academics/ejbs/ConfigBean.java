package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

@Startup
@Singleton
public class ConfigBean {
    @EJB
    private SensorBean sensorBean;

    @EJB
    private SensorPosicaoBean sensorPosicaoBean;

    @EJB
    private SensorAceleracaoBean sensorAceleracaoBean;

    @PostConstruct
    public void init() {
        System.out.println("\n\nConfigBean init\n\n");

        // Create a generic sensor
        sensorBean.create("Generic Sensor 1", 50, false, 0.0f);

        // Create a SensorPosicao with specific properties
        sensorPosicaoBean.create("Position Sensor 1", 100, true, 0.0f);

        // Create another SensorPosicao
        sensorPosicaoBean.create("Position Sensor 2", 120, false, 0.0f);

        // Create a SensorAceleracao
        sensorAceleracaoBean.create("Acceleration Sensor 1", 200, true, 0.0f);

        // Create another SensorAceleracao and detect impacts
        int accelerometerId = sensorAceleracaoBean.create("Acceleration Sensor 2", 150, true, 0.0f);
        sensorAceleracaoBean.detectarImpacto(accelerometerId, 10.5f);
        sensorAceleracaoBean.detectarImpacto(accelerometerId, 12.0f);

        System.out.println("\n\nSensors initialized in ConfigBean.\n\n");
    }
}
