
package cz.cvut.fel.ear.planIt.services;

import cz.cvut.fel.ear.planIt.dao.BaseDao;
import cz.cvut.fel.ear.planIt.dao.NoteDao;
import cz.cvut.fel.ear.planIt.model.Note;
import cz.cvut.fel.ear.planIt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoteService extends BaseService<Note>{

    private NoteDao dao;

    @Override
    protected BaseDao<Note> getDao() {
        return dao;
    }

    @Autowired
    public NoteService(NoteDao dao){ this.dao = dao; }

    @Transactional
    public void changeNameOfNote(String oldTitle, String newTitle){
        Note note = dao.findByTitle(oldTitle);
        if(note != null) {
            note.setTitle(newTitle);
            dao.update(note);
        }
    }

    @Transactional
    public void setContentToNote(String title, String content){
        Note note = dao.findByTitle(title);
        if(note != null) {
            note.setContent(content);
            dao.update(note);
        }
    }

    @Transactional
    public void deleteNoteContent(String title){
        Note note = dao.findByTitle(title);
        if(note != null) {
            note.setContent("-");
            dao.update(note);
        }
    }

}

