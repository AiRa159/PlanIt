package cz.cvut.fel.ear.planIt.model;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = "Note.findByTitle",
                query = "SELECT n FROM Note n WHERE n.title = :title")
})

@Entity
@Table(name = "notes")
public class Note extends AbstractEntity {

    @Basic(optional = false)
    @Column(name = "title", nullable = false)
    private String title;

    @Basic
    @Column(name = "content")
    private String content;

    public Note(){}

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Note(String title){
        this.title = title;
        this.content = "-";
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String toString(){
        return "Note: \nTitle: " + title
                + "\nContent: " + content
                + "\n----------------------\n";
    }
}

