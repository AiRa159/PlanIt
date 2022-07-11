package cz.cvut.fel.ear.planIt.model;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Reminder.findByName",
                query = "SELECT r FROM Reminder r where r.name = :name"),
        @NamedQuery(name = "Reminder.findByEntry",
                query = "SELECT r FROM Reminder r WHERE r.calendarEntry.id = :ce_id")
})
public class Reminder extends AbstractEntity{

    @Basic(optional = false)
    @Column(name = "date", nullable = false)
    private String time;

    @Basic(optional = false)
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "description")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "entry_id")
    private CalendarEntry calendarEntry;

    public Reminder() {
    }

    public Reminder(String time, String name, CalendarEntry calendarEntry) {
        this.time = time;
        this.name = name;
        this.calendarEntry = calendarEntry;
    }

    public Reminder(String name, CalendarEntry calendarEntry) {
        this.name = name;
        this.calendarEntry = calendarEntry;
    }

    public String getTime() { return time; }

    public void setTime(String time){
        this.time = time;
    }

    public String getName() { return name; }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription() { return description; }

    public void setDescription(String description){ this.description = description; }

    public String toString(){
        return "Reminder: " + name
                + "\nDescription: " + description
                + "\nTime: " + time
                + "\n----------------------\n";
    }
}
