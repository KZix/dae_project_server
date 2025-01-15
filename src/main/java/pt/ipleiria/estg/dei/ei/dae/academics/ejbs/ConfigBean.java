package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.SensorTemperatura;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.TipoProduto;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Encomenda;

import java.util.Date;
import java.util.List;

@Startup
@Singleton
public class ConfigBean {
    @EJB
    private SensorBean sensorBean;

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

        // Criar clientes
        clientBean.create("john_doe", "password123", "John Doe", "john.doe@example.com");
        clientBean.create("jane_doe", "password456", "Jane Doe", "jane.doe@example.com");

        try {
            // Criar sensores de temperatura
            SensorTemperatura sensor = sensorBean.createSensorTemperatura("Temperature Sensor", 50, true, 22.1f);

            // Adicionar leituras ao sensor
            sensorBean.addSensorReading(sensor.getId(), 22.5f);
            sensorBean.addSensorReading(sensor.getId(), 23.8f);
            sensorBean.addSensorReading(sensor.getId(), 21.7f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // Criar tipo de produto
            TipoProduto tipoEletronico = tipoProdutoBean.create("Electronics");
            Produto produto = produtoBean.create("Smartphone", 699.99f, tipoEletronico.getId());

            // Criar encomenda
            Encomenda encomenda = encomendaBean.create(1, new Date(), 0, List.of());  // Exemplo com cliente 1 e estado 0

            // Criar volume associado à encomenda
            Volume volume = volumeBean.create("Volume 1", List.of(produto), encomenda); // Associando o produto ao volume
        } catch (Exception e) {
            e.printStackTrace(); // Logs the exact exception
        }
    }
}