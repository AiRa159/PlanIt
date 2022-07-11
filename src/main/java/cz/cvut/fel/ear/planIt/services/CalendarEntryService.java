package cz.cvut.fel.ear.planIt.services;

import cz.cvut.fel.ear.planIt.dao.BaseDao;
import cz.cvut.fel.ear.planIt.dao.CalendarEntryDao;
import cz.cvut.fel.ear.planIt.dao.CalendarInstanceDao;
import cz.cvut.fel.ear.planIt.dao.CategoryDao;
import cz.cvut.fel.ear.planIt.model.CalendarEntry;
import cz.cvut.fel.ear.planIt.model.CalendarInstance;
import cz.cvut.fel.ear.planIt.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CalendarEntryService extends BaseService<CalendarEntry>{

    private final CalendarEntryDao dao;
    private final CalendarInstanceDao calendarDao;
    private final CategoryDao categoryDao;

    @Autowired
    public CalendarEntryService(CalendarInstanceDao calendarDao, CalendarEntryDao dao, CategoryDao categoryDao) {
        this.calendarDao = calendarDao;
        this.dao = dao;
        this.categoryDao = categoryDao;
    }

    @Override
    protected BaseDao<CalendarEntry> getDao() {
        return dao;
    }

    @Transactional(readOnly = true)
    public CalendarEntry findEntryByName(String name){
        return dao.findByName(name);
    }

    @Transactional
    public void setDescriptionToEntry(CalendarInstance ci, String nameOfEntry, String description){
        CalendarEntry entry = findEntryByName(nameOfEntry);
        if(entry != null) {
            entry.setDescription(description);
            dao.update(entry);
            calendarDao.update(ci);
        }
    }

    @Transactional
    public void deleteEntryDescription(CalendarInstance ci, String nameOfEntry){
        CalendarEntry entry = findEntryByName(nameOfEntry);
        if(entry != null) {
            entry.setDescription("-");
            dao.update(entry);
            calendarDao.update(ci);
        }
    }

    @Transactional
    public void setTimeToEntry(CalendarInstance ci, String nameOfEntry, String startTime){
        CalendarEntry entry = findEntryByName(nameOfEntry);
        if(entry != null){
                entry.setStartTime(startTime);
                dao.update(entry);
                calendarDao.update(ci);
        }
    }

    @Transactional
    public void setTimeToEntry(CalendarInstance ci, String nameOfEntry, String startTime, String endTime){
        CalendarEntry entry = findEntryByName(nameOfEntry);
        if(entry != null){
            if(converter(startTime).isBefore(converter(endTime))) {
                entry.setStartTime(startTime);
                entry.setEndTime(endTime);
                dao.update(entry);
                calendarDao.update(ci);
            }else
                throw new IllegalArgumentException("Incorrect data!");
        }
    }

    @Transactional
    public void deleteEntryTime(CalendarInstance ci, String nameOfEntry){
        CalendarEntry entry = findEntryByName(nameOfEntry);
        if(entry != null) {
            entry.setStartTime("-");
            entry.setEndTime("-");
            dao.update(entry);
            calendarDao.update(ci);
        }
    }

    public LocalDateTime converter(String time) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    @Transactional
    public void setLocationToEntry(CalendarInstance ci, String nameOfEntry, String location){
        CalendarEntry entry = findEntryByName(nameOfEntry);
        if(entry != null) {
            entry.setLocation(location);
            dao.update(entry);
            calendarDao.update(ci);
        }
    }

    @Transactional
    public void removeEntryLocation(CalendarInstance ci, String nameOfEntry){
        CalendarEntry entry = findEntryByName(nameOfEntry);
        if(entry != null) {
            entry.setLocation("-");
            dao.update(entry);
            calendarDao.update(ci);
        }
    }

    @Transactional
    public void addCategoryToEntry(CalendarInstance ci, String nameOfEntry, String color){
        CalendarEntry entry = findEntryByName(nameOfEntry);
        Category category = categoryDao.findByColor(color);
        if(entry != null && category != null) {
            entry.addCategory(category);
            dao.update(entry);
            calendarDao.update(ci);
        }
    }

    @Transactional
    public void deleteCategoryFromEntry(CalendarInstance ci, String nameOfEntry, String color){
        CalendarEntry entry = findEntryByName(nameOfEntry);
        Category category = categoryDao.findByColor(color);
        if(entry != null && category != null) {
            entry.removeCategory(category);
            dao.update(entry);
            calendarDao.update(ci);
        }
    }



}
