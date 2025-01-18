package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Client;
//import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyConstraintViolationException;
//import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityExistsException;
//import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Hasher;

import java.util.List;

@Stateless
public class ClientBean {

    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private Hasher hasher;
    /**
     * Creates and persists a new Client entity.
     *
     * @param username the username of the client
     * @param password the password of the client
     * @param name the name of the client
     * @param email the email of the client
     */


    public void create(String username, String password, String name, String email)
            //throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException
            {


        Client existingClient = entityManager.find(Client.class, username);
        if (existingClient != null) {
            //throw new MyEntityExistsException("Client already exists");
        }
        // Create a new Client entity
        try {
            var client = new Client(username,hasher.hash(password), name, email);
            entityManager.persist(client);
            entityManager.flush(); // when using Hibernate, to force it to throw a ConstraintViolationException, as in the JPA specification
        } catch (ConstraintViolationException e) {
            //throw new MyConstraintViolationException(e);
        }
    }

    public List<Client> findAll() {
        // remember, maps to: “SELECT s FROM Client s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllClients", Client.class).getResultList();
    }

    public Client find(String id) {
        var client = entityManager.find(Client.class, id);
        return client;
    }

    

    public void update(String username, String password, String name, String email) {
        Client client = entityManager.find(Client.class, username);
        if (client == null) {
            System.err.println("ERROR_STUDENT_NOT_FOUND: " + username);
            return;
        }
        entityManager.lock(client, LockModeType.OPTIMISTIC);
        client.setPassword(hasher.hash(password));
        client.setName(name);
        client.setEmail(email);
        
        entityManager.merge(client);
    }

    public void delete(String id) {
        Client client = entityManager.find(Client.class, id);
        if (client != null) {
            entityManager.remove(client);
        }
    }
}
