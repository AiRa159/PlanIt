package cz.cvut.fel.ear.planIt.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class GroupCalendar extends CalendarInstance{

    @OneToMany()
    private List<User> users;

    public GroupCalendar() {
    }

    public GroupCalendar(User owner, String name) {
        super(owner, name);
    }

    public Role getUserRights(User user) {
        if (users.contains(user)) {
            return user.getRole();
        }
        return null;
    }

    public void addNewModerator(User user){
        if(users.contains(user)){
            user.setRole(Role.MODERATOR);
        }
    }

    public void deleteModerator(User user){
        if(users.contains(user)){
            user.setRole(Role.USER);
        }
    }
}
