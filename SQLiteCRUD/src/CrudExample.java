import java.sql.*;

public class CrudExample {
    private Connection connection;

    public CrudExample() throws SQLException {
        // Connect to the database
        connection = DriverManager.getConnection("jdbc:sqlite:example.db");

        // Create the table if it doesn't exist
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, name TEXT, email TEXT, age INTEGER)");

        statement.close();
    }

    public int createUser(String name, String email, int age) throws SQLException {
        // Insert a new user into the database

        PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, email, age) VALUES (?, ?, ?)");
        statement.setString(1, name);
        statement.setString(2, email);
        statement.setInt(3, age);
        statement.executeUpdate();
        statement.close();

        ResultSet rs = statement.getGeneratedKeys();
        if(rs.next())
        {
            int last_inserted_id = rs.getInt(1);
            return last_inserted_id;
        }

//        Statement insertStatement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
//        String insertSql = String.format("INSERT INTO users (name, email, age) VALUES (`{0}`, `{1}`, `{2}`)", name2, email2, age2);
//        int res =  insertStatement.executeUpdate(insertSql);

//        return res;

        return -1;
    }

    public void updateUser(int id, String name, String email, int age) throws SQLException {
        // Update an existing user in the database
        PreparedStatement statement = connection.prepareStatement("UPDATE users SET name=?, email=?, age=? WHERE id=?");
        statement.setString(1, name);
        statement.setString(2, email);
        statement.setInt(3, age);
        statement.setInt(4, id);
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
                System.out.println(resultSet.getInt("id") + " " + resultSet.getString("name") + " " + resultSet.getString("email") + " " + resultSet.getInt("age"));
            }

            // Create a new user
            crudExample.createUser("John Doe", "johndoe@example.com", 30);

            // Update a user
//            crudExample.updateUser(1, "Jane Doe", "janedoe@example.com");

            // Delete a user
//            crudExample.deleteUser(1);

            // Display all users
            resultSet = crudExample.getUsers();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + " " + resultSet.getString("name") + " " + resultSet.getString("email") + " " + resultSet.getInt("age"));
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
