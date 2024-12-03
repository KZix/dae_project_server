package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.TipoProduto;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Volume;

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

    @EJB
    private ClientBean clientBean;

    @PostConstruct
    public void init() {
        System.out.println("\n\nConfigBean init\n\n");
        clientBean.create("john_doe", "password123", "John Doe", "john.doe@example.com");
        clientBean.create("jane_doe", "password456", "Jane Doe", "jane.doe@example.com");

        try {
            // Create sensors
            int sensorId = sensorBean.create("Temperature Sensor", 50,true,22.1f);

            // Add readings
            sensorBean.addSensorReading(sensorId, 22.5f);
            sensorBean.addSensorReading(sensorId, 23.8f);
            sensorBean.addSensorReading(sensorId, 21.7f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            TipoProduto tipoEletronico = tipoProdutoBean.create("Electronics");
            Produto produto = produtoBean.create("Smartphone", 699.99f, tipoEletronico.getId());
        } catch (Exception e) {
            e.printStackTrace(); // Logs the exact exception
        }

        // Create Volume
        // Volume volume1 = volumeBean.create("Volume 1", List.of(produto1, produto2));

        // Create Encomenda
        //encomendaBean.create(new Date(), List.of(volume1));

    }
}
