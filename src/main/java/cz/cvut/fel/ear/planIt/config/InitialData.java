package cz.cvut.fel.ear.planIt.config;

import cz.cvut.fel.ear.planIt.model.*;
import cz.cvut.fel.ear.planIt.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitialData implements ApplicationRunner{

    final PersonalCalendarService calendarService;
    final CalendarEntryService calendarEntryService;
    final UserService userService;
    final CategoryService categoryService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitialData(PersonalCalendarService calendarService, CalendarEntryService calendarEntryService, NoteService noteService, UserService userService, CategoryService categoryService, PasswordEncoder passwordEncoder) {
        this.calendarService = calendarService;
        this.calendarEntryService = calendarEntryService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = createUser("Aika", "Aiya", "Rakhimova", "123");
        CalendarEntry entry = createEntry("EntryTest", user.getPersonalCalendar());
        setNameToCategory(user.getPersonalCalendar(), "Test", "Blue");
//        addEntryToCategory(user.getPersonalCalendar(), entry.getName(), "Green");
    }

    public CalendarEntry createEntry(String name, CalendarInstance ci){
        final CalendarEntry entry = new CalendarEntry();
        entry.setName(name);
        entry.setDescription("ABC");
        entry.setLocation("DEF");
        entry.setStartTime("06.01.2022 10:00");
        entry.setEndTime("06.01.2022 11:00");
        entry.setCalendarInstance(ci);
        calendarService.createNewEntry(ci, entry);
        return entry;
    }

    public User createUser(String username, String name, String surname, String password){
        final User user= new User();
        user.setUsername(username);
        user.setName(name);
        user.setSurname(surname);
        user.setPassword(password);
        userService.createUser(user);
        return user;
    }

    public void setNameToCategory(CalendarInstance ci, String name, String color){
        categoryService.setNameToCategory(ci, name, color);
    }

    public void addEntryToCategory(CalendarInstance ci, String name, String color){
        calendarEntryService.addCategoryToEntry(ci, name, color);
    }

}
