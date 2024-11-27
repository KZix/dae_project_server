package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

@Startup
@Singleton
public class ConfigBean {
    @EJB
    private ClientBean clientBean;

    @PostConstruct
    public void init() {
        System.out.println("\n\nConfigBean init\n\n");

        clientBean.create("carlos", "123", "Carlos Ferreira", "carlos.j.ferreira@ipleiria.pt");
    }
}
