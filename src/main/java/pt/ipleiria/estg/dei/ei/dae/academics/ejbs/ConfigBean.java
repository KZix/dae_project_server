package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.TipoProduto;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Volume;

import java.net.URI;
import java.util.Date;
import java.util.List;

@Startup
@Singleton
public class ConfigBean {
    @EJB
    private SensorBean sensorBean;

    @EJB
    private SensorPosicaoBean sensorPosicaoBean;

    @EJB
    private SensorAceleracaoBean sensorAceleracaoBean;

    @EJB
    private ProdutoBean produtoBean;

    @EJB
    private VolumeBean volumeBean;

    @EJB
    private EncomendaBean encomendaBean;

    @EJB
    private TipoProdutoBean tipoProdutoBean;

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
        sensorAceleracaoBean.detectarImpacto(5, 10.5f);
        sensorAceleracaoBean.detectarImpacto(4, 12.0f);

        try {
            System.out.println("\n\nConfigBean init\n\n");
            // Initialization logic

            TipoProduto tipoEletronico = tipoProdutoBean.create("Electronics");
            Produto produto = produtoBean.create("Smartphone", 699.99f, tipoEletronico.getId());
        } catch (Exception e) {
            e.printStackTrace(); // Logs the exact exception
        }

        // Create Volume
        // Volume volume1 = volumeBean.create("Volume 1", List.of(produto1, produto2));

        // Create Encomenda
        //encomendaBean.create(new Date(), List.of(volume1));

        System.out.println("\n\nSensors initialized in ConfigBean.\n\n");
    }
}
