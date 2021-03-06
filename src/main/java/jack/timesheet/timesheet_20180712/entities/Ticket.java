package jack.timesheet.timesheet_20180712.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity(name = "Ticket")
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @JsonBackReference
    private Integer id;

    private LocalDate date;

    private String activity;

    private String type;

    private String resource;

    private float days;

    private String description;

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    public Ticket() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public float getDays() {
        return days;
    }

    public void setDays(float days) {
        this.days = days;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return String.format("{id: %s, date: %s, days: %f, resource: %s, activity: %s, description: %s}", this.id,
                this.date, this.days, this.resource, this.activity, this.description);
    }
}
