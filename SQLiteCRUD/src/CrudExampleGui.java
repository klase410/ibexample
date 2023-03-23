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
        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Name", "Email"});

        // Populate the table model with data from the database
        ResultSet resultSet = crudExample.getUsers();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            tableModel.addRow(new Object[]{id, name, email});
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

                JDialog dialog = createEditUserDialog(id, name, email);
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
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        // Create the main panel
        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 5, 5));
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

        // Create the OK button
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the name and email from the text fields
                String name = nameField.getText();
                String email = emailField.getText();

                try {
                    // Add the user to the database
                    crudExample.createUser(name, email);

                    // Add the user to the table model
                    ResultSet resultSet = crudExample.getUsers();
                    resultSet.last();
                    int id = resultSet.getInt("id");
                    tableModel.addRow(new Object[]{id, name, email});
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

    private JDialog createEditUserDialog(int id, String name, String email) {
        // Create the dialog
        JDialog dialog = new JDialog(this, "Edit User", true);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        // Create the main panel
        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 5, 5));
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

        // Create the OK button
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
// Get the name and email from the text fields

                String newName = nameField.getText();
                String newEmail = emailField.getText();

                try {
                    // Update the user in the database
                    crudExample.updateUser(id, newName, newEmail);

                    // Update the user in the table model
                    int selectedRow = table.getSelectedRow();
                    tableModel.setValueAt(newName, selectedRow, 1);
                    tableModel.setValueAt(newEmail, selectedRow, 2);

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