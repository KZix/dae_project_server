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
    private AdministratorBean administratorBean;

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
        administratorBean.create("admin", "admin", "John Admin", "admin@example.com");

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

            // Criar encomenda sem volumes
            Encomenda encomenda = encomendaBean.create("john_doe", new Date(), 0);  // Criar encomenda com cliente 1 e estado 0 (sem volumes)

            // Criar volume e associar à encomenda
            Volume volume = volumeBean.create("Volume 1", List.of(produto), encomenda); // Associando produto ao volume e volume à encomenda

            // Outra encomenda com volumes vazios
            Encomenda encomenda2 = encomendaBean.create("jane_doe", new Date(), 1);  // Encomenda 2 sem volumes ainda

            // Criar outros volumes e associar à encomenda2
            Produto produto2 = produtoBean.create("Tablet", 499.99f, tipoEletronico.getId());
            Volume volume2 = volumeBean.create("Volume 2", List.of(produto2), encomenda2);  // Associando produto ao volume2 e associando volume2 à encomenda2
        } catch (Exception e) {
            e.printStackTrace(); // Logs the exact exception
        }
    }
}