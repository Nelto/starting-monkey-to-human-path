package RPIS51.Filippov.wdad.learn.dao;

import RPIS51.Filippov.wdad.learn.xml.Note;

import java.util.Collection;

public class Notes implements NotesDAO {
    @Override
    public int insertNote(Note note) {
        return 0;
    }

    @Override
    public boolean deleteNote(Note note) {
        return false;
    }

    @Override
    public Note findNote(int id) {
        return null;
    }

    @Override
    public boolean updateNote(Note note) {
        return false;
    }

    @Override
    public boolean saveOrUpdateNote(Note note) {
        return false;
    }

    @Override
    public Collection<Note> findNotesByTitle(String title) {
        return null;
    }

    @Override
    public Collection<Note> findNoteTextFragment(String fragment) {
        return null;
    }
}
