package cz.cvut.fel.ear.planIt.model;

import javax.persistence.*;
import java.util.*;

@NamedQueries({
        @NamedQuery(name = "CalendarEntry.findByName",
                query = "SELECT ce FROM CalendarEntry ce WHERE ce.name = :name"),
        @NamedQuery(name = "CalendarEntry.findByCategory",
                query = "SELECT ce FROM CalendarEntry ce WHERE :category MEMBER OF ce.categories"),
        @NamedQuery(name = "CalendarEntry.findByInstance",
                    query = "SELECT ce FROM CalendarEntry ce WHERE ce.calendarInstance.id = :ci_id")
})

@Entity
@Table(name = "entries")
public class CalendarEntry extends AbstractEntity{

    @Basic(optional = false)
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic(optional = false)
    @Column(name = "starttime", nullable = false)
    private String startTime;

    @Basic
    @Column(name = "endtime")
    private String endTime;

    @Basic
    @Column(name = "location")
    private String location;

    @ManyToMany
    private List<Category> categories = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "calendar_id")
    private CalendarInstance calendarInstance;

    @OneToMany(mappedBy = "calendarEntry")
    private List<Reminder> reminders;

    public CalendarEntry() { }
    public CalendarEntry(String name, CalendarInstance calendarInstance) {
        this.name = name;
        this.description = "-";
        this.location = "-";
        this.startTime = "-";
        this.endTime = "-";
        this.calendarInstance = calendarInstance;
    }

    public CalendarEntry(String name, String description, CalendarInstance calendarInstance) {
        this.name = name;
        this.description = description;
        this.location = "-";
        this.startTime = "-";
        this.endTime = "-";
        this.calendarInstance = calendarInstance;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getStartTime() { return startTime; }

    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }

    public void setEndTime(String endTime) { this.endTime = endTime; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public List<Category> getCategories() { return categories; }

    public void setCategories(List<Category> categories) { this.categories = categories; }

    public void setCalendarInstance(CalendarInstance calendarInstance) { this.calendarInstance = calendarInstance; }

    public void addCategory(Category category) {
        if (categories.contains(category)) {
            throw new EntityExistsException("Entry already has this category");
        }
        categories.add(category);
    }

    public void removeCategory(Category category) {
        if (!categories.contains(category)) {
            throw new NoSuchElementException("Entry does not have this category");
        }
        categories.remove(category);
    }

    public void addReminder(Reminder reminder) {
        if (reminders.contains(reminder)) {
            throw new EntityExistsException("Entry already has this category");
        }
        reminders.add(reminder);
    }

    public void removeReminder(Reminder reminder) {
        if (!reminders.contains(reminder)) {
            throw new NoSuchElementException("Entry does not have this category");
        }
        reminders.remove(reminder);
    }

    public void showRemindersOfEntry(){
        if(reminders.size() > 0){
            for(Reminder r : reminders){
                System.out.println(r.toString());
            }
        }else
            System.out.println("This event has no reminders");
    }

    public String toString(){
        return "Event: \nName: " + name
                + "\nDescriprion: " + description
                + "\nStart time: " + startTime
                + "\nEnd time: " + endTime
                + "\nLocation: " + location
                + "\n----------------------\n";
    }
}
