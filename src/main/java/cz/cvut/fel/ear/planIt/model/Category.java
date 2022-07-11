package cz.cvut.fel.ear.planIt.model;

import cz.cvut.fel.ear.planIt.enums.Color;
import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Category.findByColor",
                query = "SELECT cat FROM Category cat WHERE cat.color = :color"),
        @NamedQuery(name = "Category.findByInstance",
                query = "SELECT cat FROM Category cat WHERE cat.calendarInstance.id = :ci_id")
})
public class Category extends AbstractEntity{

    @Basic(optional = false)
    @Column(name = "name", nullable = false)
    private String name;

    @Id
    @Enumerated(EnumType.STRING)
    private Color color;

    @ManyToOne(optional = false)
    @JoinColumn(name = "calendar_id")
    private CalendarInstance calendarInstance;

    public Category() { }

    public Category(String name, String color) {
        this.name = name;
        for(Color c : Color.values()){
            if(c.name().equals(color.toUpperCase())){
                this.color = c;
            }
        }
    }

    public Category(String name, String color, CalendarInstance calendarInstance) {
        this.name = name;
        this.calendarInstance = calendarInstance;
        for(Color c : Color.values()){
            if(c.name().equals(color)){
                this.color = c;
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String toString(){
        return "Category: \nName: " + name
                + "\nColor: " + color
                + "\n----------------------\n";
    }

}
