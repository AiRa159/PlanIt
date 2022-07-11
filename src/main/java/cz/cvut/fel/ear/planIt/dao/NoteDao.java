package cz.cvut.fel.ear.planIt.dao;

import cz.cvut.fel.ear.planIt.model.Note;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class NoteDao extends BaseDao<Note>{

    protected NoteDao() { super(Note.class); }

    public Note findByTitle(String title){
        try {
            return em.createNamedQuery("Note.findByTitle", Note.class).setParameter("title", title)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

