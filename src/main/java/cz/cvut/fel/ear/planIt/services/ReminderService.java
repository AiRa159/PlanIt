package cz.cvut.fel.ear.planIt.services;

import cz.cvut.fel.ear.planIt.dao.*;
import cz.cvut.fel.ear.planIt.model.CalendarEntry;
import cz.cvut.fel.ear.planIt.model.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReminderService extends BaseService<Reminder>{

    private final ReminderDao dao;
    private final CalendarEntryDao entryDao;

    @Autowired
    public ReminderService(CalendarEntryDao entryDao, ReminderDao dao) {
        this.dao = dao;
        this.entryDao = entryDao;
    }

    @Override
    protected BaseDao<Reminder> getDao() { return dao; }

    @Transactional
    public void createNewReminder(CalendarEntry entry, String name){
        Reminder reminder = new Reminder(name, entry);
        if(reminder != null){
            entry.addReminder(reminder);
            dao.persist(reminder);
            entryDao.update(entry);
        }
    }

    @Transactional
    public void deleteReminder(CalendarEntry entry, String name){
        Reminder reminder = dao.findByName(name);
        if(reminder != null){
            entry.removeReminder(reminder);
            dao.remove(reminder);
            entryDao.update(entry);
        }
    }

    @Transactional
    public void changeNameOfReminder(CalendarEntry entry, String oldName, String newName){
        Reminder reminder = dao.findByName(oldName);
        if(reminder != null) {
            reminder.setName(newName);
            dao.update(reminder);
            entryDao.update(entry);
        }
    }

    @Transactional
    public void setDescriptionToRemider(CalendarEntry entry, String name, String description){
        Reminder reminder = dao.findByName(name);
        if(reminder != null) {
            reminder.setDescription(description);
            dao.update(reminder);
            entryDao.update(entry);
        }
    }

    @Transactional
    public void deleteReminderDescription(CalendarEntry entry, String name) {
        Reminder reminder = dao.findByName(name);
        if (reminder != null) {
            reminder.setDescription("-");
            dao.update(reminder);
            entryDao.update(entry);
        }
    }

    @Transactional
    public void setTimeToRemider(CalendarEntry entry, String name, String time){
        Reminder reminder = dao.findByName(name);
        if(reminder != null){
            if(converter(entry.getEndTime()).isAfter(converter(time))) {
                reminder.setTime(time);
                dao.update(reminder);
                entryDao.update(entry);
            }else
                throw new IllegalArgumentException("Incorrect data!");
        }
    }

    public LocalDateTime converter(String time) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    @Transactional
    public void deleteReminderTime(CalendarEntry entry, String name){
        Reminder reminder = dao.findByName(name);
        if(reminder != null) {
            reminder.setTime("-");
            dao.update(reminder);
            entryDao.update(entry);
        }
    }


}
