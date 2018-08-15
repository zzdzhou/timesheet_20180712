package jack.timesheet.timesheet_20180712.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.annotation.Generated;
import javax.persistence.*;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(unique = true)
    private String fullName;

    @Column(nullable = false)
    private String password;

    private String email;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Ticket> tickets;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
    @StaticMetamodel(User.class)
    public abstract static class User_ {

        public static volatile SingularAttribute<User, String> password;
        public static volatile ListAttribute<User, Ticket> tickets;
        public static volatile SingularAttribute<User, String> fullName;
        public static volatile SingularAttribute<User, Integer> id;
        public static volatile SingularAttribute<User, String> email;
        public static volatile SingularAttribute<User, String> username;

        public static final String PASSWORD = "password";
        public static final String TICKETS = "tickets";
        public static final String FULL_NAME = "fullName";
        public static final String ID = "id";
        public static final String EMAIL = "email";
        public static final String USERNAME = "username";

    }
}
