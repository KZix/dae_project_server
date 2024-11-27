package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllClients",
                query = "SELECT s FROM Client s ORDER BY s.name" // JPQL
        )
})


public class Client extends User{

    @NotNull

    // Default empty constructor
    public Client() {
        
    }

    // Constructor with all attributes
    public Client(String username, String password, String name, String email) {
        super(username, password, name, email);
    }








}
