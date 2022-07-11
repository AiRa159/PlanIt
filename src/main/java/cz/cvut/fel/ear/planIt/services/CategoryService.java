package cz.cvut.fel.ear.planIt.services;

import cz.cvut.fel.ear.planIt.dao.BaseDao;
import cz.cvut.fel.ear.planIt.dao.CalendarInstanceDao;
import cz.cvut.fel.ear.planIt.dao.CategoryDao;
import cz.cvut.fel.ear.planIt.enums.Color;
import cz.cvut.fel.ear.planIt.model.CalendarInstance;
import cz.cvut.fel.ear.planIt.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService extends BaseService<Category> {

    private final CategoryDao dao;
    private final CalendarInstanceDao calendarDao;

    @Autowired
    public CategoryService(CalendarInstanceDao calendarDao, CategoryDao dao) {
        this.dao = dao;
        this.calendarDao = calendarDao;
    }

    @Override
    protected BaseDao<Category> getDao() {
        return dao;
    }

    @Transactional
    public Category findCategory(String color){
        return dao.findByColor(color);
    }

    @Transactional
    public void setNameToCategory(CalendarInstance ci, String nameOfCategory, String color){
        if(color.equalsIgnoreCase(Color.GREEN.name()) || color.equalsIgnoreCase(Color.RED.name())
                || color.equalsIgnoreCase(Color.ORANGE.name()) || color.equalsIgnoreCase(Color.YELLOW.name())){
            System.out.println("You can't change the name of this category!");
        }else if(color.equalsIgnoreCase(Color.BLACK.name()) || color.equalsIgnoreCase(Color.BLUE.name())
                || color.equalsIgnoreCase(Color.BROWN.name()) || color.equalsIgnoreCase(Color.GREY.name())
                ||  color.equalsIgnoreCase(Color.PURPLE.name()) || color.equalsIgnoreCase(Color.PINK.name())) {
//            ci.setNameToCategory(nameOfCategory, color);
            Category cat = dao.findByColor(color);
            cat.setName(nameOfCategory);
            dao.update(cat);
            calendarDao.update(ci);
        }
    }

    @Transactional
    public void resetNameOfCategory(CalendarInstance ci, String color){
        if(color.equalsIgnoreCase(Color.GREEN.name()) || color.equalsIgnoreCase(Color.RED.name())
                || color.equalsIgnoreCase(Color.ORANGE.name()) || color.equalsIgnoreCase(Color.YELLOW.name())){
            System.out.println("You can't reset the name of this category!");
        }else if(color.equalsIgnoreCase(Color.BLACK.name()) || color.equalsIgnoreCase(Color.BLUE.name())
                || color.equalsIgnoreCase(Color.BROWN.name()) || color.equalsIgnoreCase(Color.GREY.name())
                ||  color.equalsIgnoreCase(Color.PURPLE.name()) || color.equalsIgnoreCase(Color.PINK.name())) {
//            ci.resetNameOfCategory(color);
            Category cat = dao.findByColor(color);
            dao.update(cat);
            calendarDao.update(ci);
        }
    }

}
