package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Startup
@Singleton
public class ConfigBean {

    @EJB
    private ClientBean clientBean;

    @EJB
    private ProdutoBean produtoBean;

    @EJB
    private TipoProdutoBean tipoProdutoBean;

    @EJB
    private EncomendaBean encomendaBean;

    @EJB
    private VolumeBean volumeBean;

    @EJB
    private AdministratorBean administratorBean;

    @EJB
    private SensorBean sensorBean;

    @PostConstruct
    public void init() {
        System.out.println("\n\nConfigBean initialized\n\n");

        try {
            // Criar clientes
            clientBean.create("john_doe", "password123", "John Doe", "john.doe@example.com");
            clientBean.create("jane_doe", "password456", "Jane Doe", "jane.doe@example.com");
            administratorBean.create("admin", "admin", "John Admin", "admin@example.com");

            // Criar tipos de produto
            TipoProduto tipoEletronico = tipoProdutoBean.create("Eletrônicos");
            TipoProduto tipoDomestico = tipoProdutoBean.create("Domésticos");

            // Criar produtos
            Produto produto1 = produtoBean.create("Smartphone", 699.99F, tipoEletronico.getId());
            Produto produto2 = produtoBean.create("Tablet", 499.99F, tipoEletronico.getId());
            Produto produto3 = produtoBean.create("Microondas", 150.00F, tipoDomestico.getId());

            // Criar encomendas
            Encomenda encomenda1 = encomendaBean.create("john_doe",0);
            Encomenda encomenda2 = encomendaBean.create("jane_doe",1);

            // Criar volumes e associar às encomendas
            Volume volume1 = volumeBean.create("Volume 1",0, encomenda1.getId());
            Volume volume2 = volumeBean.create("Volume 2",0, encomenda1.getId());
            Volume volume3 = volumeBean.create("Volume 3",0, encomenda2.getId());

            //adicionar produtos aos volumes
            List<Integer> produtoIds = Arrays.asList(produto1.getId(), produto2.getId(), produto3.getId());
            volumeBean.addProdutosToVolume(produtoIds,volume1.getId());

            // Criar sensores
            SensorAceleracao sensorAceleracao = sensorBean.createSensorAceleracao("Sensor Impacto", 5, true, 0);
            SensorPosicao sensorPosicao = sensorBean.createSensorPosicao("Sensor Posicional", 10, true, 1.5F);
            SensorTemperatura sensorTemperatura = sensorBean.createSensorTemperatura("Sensor de Temperatura", 3, true, 2.0F);

            // Adicionar leituras aos sensores
            sensorBean.addSensorReading(sensorAceleracao.getId(), 12.5F); // Detecta impacto
            sensorBean.addSensorReading(sensorAceleracao.getId(), 9.0F); // Não detecta impacto

            sensorBean.addPositionReading(sensorPosicao.getId(), 3.2F);
            sensorBean.addPositionReading(sensorPosicao.getId(), 4.5F);

            sensorBean.addTemperatureReading(sensorTemperatura.getId(), 22.5F);
            sensorBean.addTemperatureReading(sensorTemperatura.getId(), 18.0F);


            System.out.println("Dados iniciais criados com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao inicializar o banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}