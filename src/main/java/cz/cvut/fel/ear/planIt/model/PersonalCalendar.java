package cz.cvut.fel.ear.planIt.model;

import javax.persistence.Entity;


@Entity
public class PersonalCalendar extends CalendarInstance{

    public PersonalCalendar() { }

    public PersonalCalendar(User owner, String name) {
        super(owner, name);
    }

}
