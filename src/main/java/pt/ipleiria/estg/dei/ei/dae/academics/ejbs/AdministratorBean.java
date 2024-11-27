package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.constraints.Email;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Administrator;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Hasher;

@Stateless
public class AdministratorBean {
    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private Hasher hasher;
    public void create(String username, String password, String name, String email) {
        Administrator admin = new Administrator(username,hasher.hash(password),name,email);
        entityManager.persist(admin);
    }
    public void update(String username, String password, String name, String email) {
        Administrator administrator = entityManager.find(Administrator.class, username);
        if (administrator == null) {
            System.err.println("ERROR_ADMIN_NOT_FOUND: " + username);
            return;
        }
        entityManager.lock(administrator, LockModeType.OPTIMISTIC);
        administrator.setPassword(hasher.hash(password));
        administrator.setName(name);
        administrator.setEmail(email);
        entityManager.merge(administrator);
    }
}
