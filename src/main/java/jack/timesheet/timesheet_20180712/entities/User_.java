package jack.timesheet.timesheet_20180712.entities;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
public class User_ {

    public static volatile SingularAttribute<User, String> password;
    public static volatile ListAttribute<User, Ticket> tickets;
    public static volatile SingularAttribute<User, String> fullName;
    public static volatile SingularAttribute<User, Integer> id;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, String> username;

}
