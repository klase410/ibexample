import java.util.ArrayList;
import java.util.List;

public class PasswordManager {
    private List<Password> passwords = new ArrayList<>();

    public void addPassword(Password password) {
        passwords.add(password);
    }

    public void editPassword(int index, Password password) {
        passwords.set(index, password);
    }

    public void deletePassword(int index) {
        passwords.remove(index);
    }

    public Password getPassword(int index) {
        return passwords.get(index);
    }

    public List<Password> getAllPasswords() {
        return passwords;
    }
}
