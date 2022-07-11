package cz.cvut.fel.ear.planIt.model;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;

@Entity
@NamedQueries({
        @NamedQuery(name = "User.findByUsername",
                query = "SELECT u FROM User u where u.username = :username"),
        @NamedQuery(name = "User.findByName",
                query = "SELECT u FROM User u WHERE u.name = :name and u.surname = :surname")
})
@Table(name = "users")
public class User extends AbstractEntity implements Serializable {

    private static User user;

    @Basic(optional = false)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Basic(optional = false)
    @Column(name = "name", nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(name = "surname", nullable = false)
    private String surname;

    @Basic(optional = false)
    @Column(name = "password", nullable = false)
    private String password;

//    @ManyToOne
//    @JoinColumn(name = "groupCalendar", nullable = true)
//    private GroupCalendar groupCalendar;

    @OneToOne(mappedBy ="owner", cascade = CascadeType.PERSIST)
    private PersonalCalendar personalCalendar;

    @OneToMany
    private List<Note> notes;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
        this.personalCalendar = new PersonalCalendar(this, username);
        setRole(Role.USER);
    }

    public User(String username, String name, String surname, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.personalCalendar = new PersonalCalendar(this, username);
        this.role = Role.USER;
       // this.groupCalendar = null;
    }

    public void setUsername(String username) {
        this.username = username;
        if(personalCalendar.getName() == null){
            personalCalendar.setName(username);
        }
    }

    public String getUsername() { return username; }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public void setPassword(String password) { this.password = password; }

    public String getPassword() { return password; }

    public void encodePassword(PasswordEncoder encoder) { this.password = encoder.encode(password); }

    public void erasePassword() { this.password = null; }

    public PersonalCalendar getPersonalCalendar() { return personalCalendar; }

    public String toString(){
        return "User: \nUsername: " + username
                + "\nName: " + name
                + "\nSurname: " + surname
                + "\nPassword: " + password + "\n";
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

   // public void deleteGroupCalendar(){ this.groupCalendar = null; }

    public void addNote(Note note){
        if(notes.contains(note)){
            throw new EntityExistsException("Note already exists");
        }
        notes.add(note);
    }

    public void removeNote(Note note){
        if(!notes.contains(note)){
            throw new NoSuchElementException("Note does not exist");
        }
        notes.remove(note);
    }
}
