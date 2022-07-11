package cz.cvut.fel.ear.planIt.services;

import cz.cvut.fel.ear.planIt.dao.*;
import cz.cvut.fel.ear.planIt.enums.Color;
import cz.cvut.fel.ear.planIt.model.CalendarEntry;
import cz.cvut.fel.ear.planIt.model.CalendarInstance;
import cz.cvut.fel.ear.planIt.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public abstract class CalendarService extends BaseService<CalendarInstance>{

    protected final CalendarInstanceDao dao;
    protected final CalendarEntryDao entryDao;
    protected final CategoryDao categoryDao;
    protected final UserDao userDao;

    @Autowired
    public CalendarService(CalendarInstanceDao dao, CalendarEntryDao entryDao, CategoryDao categoryDao, UserDao userDao) {
        this.dao = dao;
        this.entryDao = entryDao;
        this.categoryDao = categoryDao;
        this.userDao = userDao;
    }

    @Override
    protected BaseDao<CalendarInstance> getDao() {
        return dao;
    }

    @Transactional(readOnly = true)
    public List<CalendarEntry> findAllEntries(CalendarInstance ci) {return entryDao.findAllEntriesByInstance(ci);}

    @Transactional(readOnly = true)
    public List<CalendarEntry> findAllEntriesByCategory(Category category) {return entryDao.findAllByCategory(category);}

    @Transactional(readOnly = true)
    public List<Category> findAllCategories(CalendarInstance ci) {return categoryDao.findAllCategoriesByInstance(ci);}

    @Transactional(readOnly = true)
    public CalendarEntry findEntryByName(String name){
        return entryDao.findByName(name);
    }

    @Transactional
    public boolean isEntryExist(CalendarInstance ci, CalendarEntry entry){
        return entryDao.findAllEntriesByInstance(ci).contains(entry);
    }

    @Transactional
    public void createNewEntry(CalendarInstance ci, CalendarEntry entry){
        if(!isEntryExist(ci, entry)){
            ci.addEntry(entry);
            entryDao.persist(entry);
            dao.update(ci);
        }
    }

    @Transactional
    public void deleteEntryByName(CalendarInstance ci, String name){
        CalendarEntry entry = findEntryByName(name);
        if(entry != null) {
            ci.deleteEntry(entry);
            entryDao.remove(entry);
            dao.update(ci);
        }
    }
}
