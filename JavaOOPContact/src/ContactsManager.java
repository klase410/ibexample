import java.util.ArrayList;
import java.util.List;

public class ContactsManager {
    private List<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void editContact(int index, Contact contact) {
        contacts.set(index, contact);
    }

    public void deleteContact(int index) {
        contacts.remove(index);
    }

    public Contact getContact(int index) {
        return contacts.get(index);
    }

    public List<Contact> getAllContacts() {
        return contacts;
    }
}
