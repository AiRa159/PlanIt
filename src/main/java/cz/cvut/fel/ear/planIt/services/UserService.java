package cz.cvut.fel.ear.planIt.services;

import cz.cvut.fel.ear.planIt.dao.NoteDao;
import cz.cvut.fel.ear.planIt.dao.UserDao;
import cz.cvut.fel.ear.planIt.dao.BaseDao;
import cz.cvut.fel.ear.planIt.model.CalendarInstance;
import cz.cvut.fel.ear.planIt.model.Note;
import cz.cvut.fel.ear.planIt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserService extends BaseService<User> {

    private final UserDao dao;
    private final NoteDao noteDao;
    private final PasswordEncoder passwordEncoder;

    final User currUser = new User(); // singleton simulating logged-in user

    @Autowired
    public UserService(UserDao dao, NoteDao noteDao, PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.noteDao = noteDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected BaseDao<User> getDao() {
        return dao;
    }

    @Transactional(readOnly = true)
    public CalendarInstance getCurrentUserPersonalCalendar() {
        return currUser.getPersonalCalendar();
    }

    @Transactional(readOnly = true)
    public void showEvents() { getCurrentUserPersonalCalendar().showEntries(); }

    @Transactional(readOnly = true)
    public String showInfoAboutEntry(String nameOfEntry) {
        return getCurrentUserPersonalCalendar().getInfoAboutEntry(nameOfEntry);
    }

    @Transactional(readOnly = true)
    public void showCategories() { getCurrentUserPersonalCalendar().showCategories(); }

    @Transactional
    public void addNote(Note note){
        Objects.requireNonNull(note);
        currUser.addNote(note);
        noteDao.persist(note);
        dao.update(currUser);
    }

    @Transactional
    public void deleteNote(String title){
        Note note = noteDao.findByTitle(title);
        if(note != null){
            currUser.removeNote(note);
            noteDao.remove(note);
            dao.update(currUser);
        }
    }

    @Transactional
    public void createUser(User user){
        if(user != null){
            user.encodePassword(passwordEncoder);
            dao.persist(user);
        }
    }
}
