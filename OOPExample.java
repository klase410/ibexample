public class Contact {
    private String name;
    private String phoneNumber;
    
    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

public class BusinessContact extends Contact {
    private String businessName;
    
    public BusinessContact(String name, String phoneNumber, String businessName) {
        super(name, phoneNumber);
        this.businessName = businessName;
    }
    
    public String getBusinessName() {
        return businessName;
    }
}

public class PersonalContact extends Contact {
    private String email;
    
    public PersonalContact(String name, String phoneNumber, String email) {
        super(name, phoneNumber);
        this.email = email;
    }
    
    public String getEmail() {
        return email;
    }
}

public class ContactManager {
    private List<Contact> contacts;
    
    public ContactManager() {
        contacts = new ArrayList<Contact>();
    }
    
    public void addContact(Contact contact) {
        contacts.add(contact);
    }
    
    public void displayContacts() {
        for (Contact contact : contacts) {
            System.out.println(contact.getName() + ": " + contact.getPhoneNumber());
        }
    }
}

