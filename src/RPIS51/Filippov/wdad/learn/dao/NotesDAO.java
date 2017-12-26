package RPIS51.Filippov.wdad.learn.dao;

import RPIS51.Filippov.wdad.learn.xml.Note;

import java.util.Collection;

public interface NotesDAO {
    public int insertNote (Note note);
    public boolean deleteNote (Note note);
    public Note findNote (int id);
    public boolean updateNote (Note note);
    public boolean saveOrUpdateNote (Note note);
    public Collection<Note> findNotesByTitle (String title);
    public Collection<Note> findNoteTextFragment (String fragment);
}
