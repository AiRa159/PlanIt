package cz.cvut.fel.ear.planIt.model;

import cz.cvut.fel.ear.planIt.enums.Color;
import  javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "CalendarInstance.findByName",
                query = "SELECT ci FROM CalendarInstance ci where ci.name = :name"),
        @NamedQuery(name = "CalendarInstance.findByOwner",
                query = "SELECT ci FROM CalendarInstance ci WHERE ci.owner.id = :owner_id")
})
@Inheritance(
        strategy = InheritanceType.JOINED
)
public class CalendarInstance extends AbstractEntity {
    
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @Basic(optional = false)
    @Column(name = "name", nullable = true)
    private String name;

    @OneToMany(mappedBy = "calendarInstance", cascade = CascadeType.PERSIST)
    @JoinColumn(name = "entry_id")
    @OrderBy("startTime")
    private List<CalendarEntry> entries = new ArrayList<>();

    @OneToMany(mappedBy = "calendarInstance", cascade = CascadeType.PERSIST)
    @JoinColumn(name = "calendar_id")
    private final List<Category> categories = new ArrayList<>();

    public CalendarInstance() {
        for(Color c : Color.values()){
            this.categories.add(new Category(c.getLabel(), c.toString(), this));
        }
    }

    public CalendarInstance(User owner, String name) {
        setOwner(owner);
        setName(name);
        for(Color c : Color.values()){
            this.categories.add(new Category(c.getLabel(), c.toString(), this));
        }
    }

    public List<Category> getCategories() {
        return categories;
    }

    public User getOwner() { return owner; }

    public void setOwner(User owner) { this.owner = owner; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public void addEntry(CalendarEntry entry){
        entries.add(entry);
    }

    public void deleteEntry(CalendarEntry entry){
        entries.remove(entry);
    }

    public List<CalendarEntry> getEntries(){
        return entries;
    }

    public String getInfoAboutEntry(String name){
        for(CalendarEntry entry : entries){
            if(entry.getName().equals(name)){
                return entry.toString();
            }
        }
        return "Event with this name does not exist";
    }

    public void showEntries(){
        if(entries.size() > 0) {
            for (CalendarEntry entry : entries) {
                System.out.println(entry.toString());
            }
        }else
            System.out.println("You haven't got events");
    }

    public void setNameToCategory(String nameOfCategory, String color){
        for(Category category : categories){
            if(category.getColor().name().equalsIgnoreCase(color)){
                category.setName(nameOfCategory);
            }
        }
    }

    public void resetNameOfCategory(String color){
        for(Category category : categories){
            if(category.getColor().name().equalsIgnoreCase(color)){
                category.setName("");
            }
        }
    }

    public String getNameOfCategoryByColor(String color){
        for(Category category : categories){
            if(category.getColor().name().equalsIgnoreCase(color)){
                return category.getName();
            }
        }
        return "This color does not exist";
    }

    public void showCategories(){
        for(Category category : categories){
            System.out.println(category.toString());
        }
    }
}
