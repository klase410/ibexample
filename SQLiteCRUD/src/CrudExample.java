import java.sql.*;

public class CrudExample {
    private Connection connection;

    public CrudExample() throws SQLException {
        // Connect to the database
        connection = DriverManager.getConnection("jdbc:sqlite:example.db");

        // Create the table if it doesn't exist
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, name TEXT, email TEXT)");

        statement.close();
    }

    public void createUser(String name, String email) throws SQLException {
        // Insert a new user into the database
        PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, email) VALUES (?, ?)");
        statement.setString(1, name);
        statement.setString(2, email);
        statement.executeUpdate();

        statement.close();
    }

    public void updateUser(int id, String name, String email) throws SQLException {
        // Update an existing user in the database
        PreparedStatement statement = connection.prepareStatement("UPDATE users SET name=?, email=? WHERE id=?");
        statement.setString(1, name);
        statement.setString(2, email);
        statement.setInt(3, id);
        statement.executeUpdate();

        statement.close();
    }

    public void deleteUser(int id) throws SQLException {
        // Delete a user from the database
        PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id=?");
        statement.setInt(1, id);
        statement.executeUpdate();

        statement.close();
    }

    public ResultSet getUsers() throws SQLException {
        // Get all users from the database
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

        return resultSet;
    }

    public static void main(String[] args) {
        try {
            CrudExample crudExample = new CrudExample();

            // Display all users
            ResultSet resultSet = crudExample.getUsers();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + " " + resultSet.getString("name") + " " + resultSet.getString("email"));
            }

            // Create a new user
            crudExample.createUser("John Doe", "johndoe@example.com");

            // Update a user
//            crudExample.updateUser(1, "Jane Doe", "janedoe@example.com");

            // Delete a user
//            crudExample.deleteUser(1);

            // Display all users
            resultSet = crudExample.getUsers();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + " " + resultSet.getString("name") + " " + resultSet.getString("email"));
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
