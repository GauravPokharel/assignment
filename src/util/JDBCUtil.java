package util;

import entity.Student;

import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class JDBCUtil {
    String url;
    String userName;
    String password;
    Connection connection;

    public JDBCUtil() {
        this.url = "jdbc:mysql://localhost:3306/test_database";
        this.userName = "root";
        this.password = "mysql";
        try {
            this.connection = jdbcConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private Connection jdbcConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(url, userName, password);
    }

    public List<Student> selectQuery() {
        String selectQuery = "Select * from students";
        ResultSet resultSet = null;
        List<Student> students = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt(1));
                student.setName(resultSet.getString(2));
                student.setAddress(resultSet.getString(3));
                student.setClassLevel(resultSet.getInt(4));
                student.setRollNumber(resultSet.getInt(5));
                students.add(student);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return students;
    }

    public void updateRollNumberQuery(List<Student> students) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Unable to create statement.");
        }
        AtomicReference<Integer> rollNumber = new AtomicReference<>(1);
        Statement finalStatement = statement;
        students.forEach(
                student -> {
                    String updateQuery = "UPDATE students SET id=" + student.getId() + ", " +
                            "name= \'" + student.getName() + "\' , " +
                            "address= \'" + student.getAddress() + "\' , " +
                            "class= \'" + student.getClassLevel() + "\' , " +
                            "roll_number= \'" + rollNumber.get() +"\' "+
                            "where id=" + student.getId();
                    rollNumber.getAndSet(rollNumber.get() + 1);
                    try {
                        finalStatement.executeUpdate(updateQuery);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //table is created as :
/*
    private void createTable() throws SQLException {
     Statement statement = connection.createStatement();
        String query = "create table students(" +
                "id int(20) primary key auto_increment," +
                "name varchar(200) not null," +
                "address varchar(400) not null," +
                "class int(10) not null," +
                "roll_number int(20)" +
                ")";
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        System.out.println("Student table created.");
                connection.close();

    }*/


    //data are manually inserted using insert query like:
    /*
    private void insertTable() throws SQLException {
     Statement statement = connection.createStatement();
        String sqlInsert = "INSERT INTO students VALUES ('1', 'John Doe', 'Kathmandu', '1' ,NULL)";
        statement.executeUpdate(sqlInsert);
    sqlInsert = "INSERT INTO students VALUES ('1', 'Mery ', 'Kathmandu', '1' ,NULL)";
        statement.executeUpdate(sqlInsert);
    sqlInsert = "INSERT INTO students VALUES ('', 'Krishna ', 'Kathmandu', '2' ,NULL)";
        statement.executeUpdate(sqlInsert);
    sqlInsert = "INSERT INTO students VALUES ('', 'Ram ', 'BKT', '2' ,NULL)";
        statement.executeUpdate(sqlInsert);
                connection.close();

    }*/

}
