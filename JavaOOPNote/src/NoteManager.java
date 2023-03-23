import java.util.ArrayList;
import java.util.List;

public class NoteManager {
    private List<Note> notes = new ArrayList<>();

    public void addNote(Note note) {
        notes.add(note);
    }

    public void editNote(int index, Note note) {
        notes.set(index, note);
    }

    public void deleteNote(int index) {
        notes.remove(index);
    }

    public Note getNote(int index) {
        return notes.get(index);
    }

    public List<Note> getAllNotes() {
        return notes;
    }
}
