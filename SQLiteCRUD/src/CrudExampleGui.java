import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudExampleGui extends JFrame implements ActionListener {
    private CrudExample crudExample;
    private JTable table;
    private DefaultTableModel tableModel;

    public CrudExampleGui() throws SQLException {
        super("User Management");

        // Connect to the database
        crudExample = new CrudExample();

        // Create the main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create the table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Users"));

        // Create the table model
        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Name", "Email", "Age" });

        // Populate the table model with data from the database
        ResultSet resultSet = crudExample.getUsers();

        System.out.print(resultSet.toString());

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            int age = resultSet.getInt("age");
            tableModel.addRow(new Object[]{id, name, email, age});
        }
        resultSet.close();

        // Create the table
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 200));
        table.setFillsViewportHeight(true);

        // Add the table to the table panel
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Create the button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());

        // Create the add button
        JButton addButton = new JButton("Add");
        addButton.addActionListener(this);
        buttonPanel.add(addButton);

        // Create the edit button
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(this);
        buttonPanel.add(editButton);

        // Create the delete button
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);
        buttonPanel.add(deleteButton);

        // Add the button panel to the main panel
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the table panel to the main panel
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Add the main panel to the frame
        add(mainPanel);

        // Set the frame properties
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            new CrudExampleGui();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add")) {
            // Create a dialog to add a new user
            JDialog dialog = createAddUserDialog();
            dialog.setVisible(true);
        } else if (e.getActionCommand().equals("Edit")) {
            // Get the selected row from the table
            int selectedRow = table.getSelectedRow();

            // If a row is selected, create a dialog to edit the user
            if (selectedRow >= 0) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                String name = (String) tableModel.getValueAt(selectedRow, 1);
                String email = (String) tableModel.getValueAt(selectedRow, 2);
                int age = (int) tableModel.getValueAt(selectedRow, 3);

                JDialog dialog = createEditUserDialog(id, name, email, age);
                dialog.setVisible(true);
            }
        } else if (e.getActionCommand().equals("Delete")) {
            // Get the selected row from the table
            int selectedRow = table.getSelectedRow();

            // If a row is selected, delete the user
            if (selectedRow >= 0) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                try {
                    crudExample.deleteUser(id);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Failed to delete user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

                // Remove the row from the table model
                tableModel.removeRow(selectedRow);
            }
        }
    }

    private JDialog createAddUserDialog() {
        // Create the dialog
        JDialog dialog = new JDialog(this, "Add User", true);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setSize(500, 150);
        dialog.setLocationRelativeTo(this);

        // Create the main panel
        JPanel mainPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Create the name label and text field
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        mainPanel.add(nameLabel);
        mainPanel.add(nameField);

        // Create the email label and text field
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        mainPanel.add(emailLabel);
        mainPanel.add(emailField);

        // Create the email label and text field
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();
        mainPanel.add(ageLabel);
        mainPanel.add(ageField);

        // Create the OK button
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the name and email from the text fields
                String name = nameField.getText();
                String email = emailField.getText();
                int age = Integer.parseInt(ageField.getText()); // int age = 30; -- initial code

                try {
                    // Add the user to the database
                    int id = crudExample.createUser(name, email, age);

                    // Add the user to the table model
                    ResultSet resultSet = crudExample.getUsers();
//                    resultSet.last();
//                    int id = resultSet.getInt("id");
                    tableModel.addRow(new Object[]{id, name, email, age}); // TODO: Fix TYPE FORWARD ONLY
                    resultSet.close();

//                    tableModel

//                    // clean all rows
//                    int rowCount = tableModel.getRowCount();
//                    //Remove rows one by one from the end of the table
//                    for (int i = rowCount - 1; i >= 0; i--) {
//                        tableModel.removeRow(i);
//                    }
//
//                    // load data from the database
//                    while (resultSet.next()) {
//                        int id1 = resultSet.getInt("id");
//                        String name1 = resultSet.getString("name");
//                        String email1 = resultSet.getString("email");
//                        int age1 = resultSet.getInt("age");
//                        tableModel.addRow(new Object[]{id1, name1, email1, age1});
//                    }
                    resultSet.close();

                    // Close the dialog
                    dialog.dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(dialog, "Failed to add user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        mainPanel.add(okButton);

        // Create the cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close the dialog
                dialog.dispose();
            }
        });
        mainPanel.add(cancelButton);

        // Add the main panel to the dialog
        dialog.add(mainPanel);

        return dialog;
    }

    private JDialog createEditUserDialog(int id, String name, String email, int age) {
        // Create the dialog
        JDialog dialog = new JDialog(this, "Edit User", true);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setSize(500, 150);
        dialog.setLocationRelativeTo(this);

        // Create the main panel
        JPanel mainPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Create the name label and text field
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(name);
        mainPanel.add(nameLabel);
        mainPanel.add(nameField);

        // Create the email label and text field
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(email);
        mainPanel.add(emailLabel);
        mainPanel.add(emailField);

        // Create the age label and text field
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField(String.valueOf(age));
        mainPanel.add(ageLabel);
        mainPanel.add(ageField);

        // Create the OK button
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
// Get the name and email from the text fields

                String newName = nameField.getText();
                String newEmail = emailField.getText();
                int newAge = Integer.parseInt(ageField.getText()); // int newAge = 30; -- initial code

                try {
                    // Update the user in the database
                    crudExample.updateUser(id, newName, newEmail, newAge);

                    // Update the user in the table model
                    int selectedRow = table.getSelectedRow();
                    tableModel.setValueAt(newName, selectedRow, 1);
                    tableModel.setValueAt(newEmail, selectedRow, 2);
                    tableModel.setValueAt(newAge, selectedRow, 3);

                    // Close the dialog
                    dialog.dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(dialog, "Failed to update user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        mainPanel.add(okButton);

        // Create the cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close the dialog
                dialog.dispose();
            }
        });
        mainPanel.add(cancelButton);

        // Add the main panel to the dialog
        dialog.add(mainPanel);

        return dialog;
    }
}