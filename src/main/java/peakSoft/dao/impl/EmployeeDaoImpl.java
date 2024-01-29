package peakSoft.dao.impl;

import peakSoft.config.JdbcConf;
import peakSoft.dao.EmployeeDao;
import peakSoft.model.Employee;
import peakSoft.model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements EmployeeDao {

    private final Connection connection = JdbcConf.getConnection();

    @Override
    public void createEmployee() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("""
create table if not exists employees (
id serial primary key,
first_name varchar,
last_name varchar,
age int,
email varchar,
job_id int references jobs(id));
""");
statement.close();

}catch (SQLException e){
    System.out.println(e.getMessage());
}
    }

    @Override
    public void addEmployee(Employee employee) {
String query= "insert into employees(first_name, last_name, age, email, job_id)" +
        "values(?, ?, ?, ?, ?); ";
        try(PreparedStatement preparedStatement= connection.prepareStatement(query)){
preparedStatement.setString(1, employee.getFirstName());
preparedStatement.setString(2, employee.getLastName());
preparedStatement.setInt(3, employee.getAge());
preparedStatement.setString(4,employee.getEmail());
preparedStatement.setLong(5, employee.getJobId());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                System.out.println("Insertion failed, no rows affected.");
            } else {
                System.out.println("User successfully inserted.");
            }


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void dropTable() {
try(Statement statement = connection.createStatement()){
   statement.executeUpdate("drop table if exists employees");
}catch (SQLException e){
    System.out.println(e.getMessage());
}

    }

    @Override
    public void cleanTable() {
try(Statement statement = connection.createStatement()){
    statement.executeUpdate("truncate table employees");

}catch (SQLException e){
    System.out.println(e.getMessage());
}
    }

    @Override
    public void updateEmployee(Long id, Employee employee) {
        String query = "update employees set first_name=?, last_name=?, age=?, email=?, job_id=? where id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2,employee.getLastName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setLong(5,employee.getJobId());
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employees";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                employees.add(extractEmployeeFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return employees;
    }

    @Override
    public Employee findByEmail(String email) {
        String query="select * from employees where email = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(4,email);
            ResultSet resultSet= preparedStatement.executeQuery();

            if(resultSet.next()){
                return extractEmployeeFromResultSet(resultSet);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }return null;
    }


    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        Map<Employee, Job> employeeJobMap = new HashMap<>();
        String query = "SELECT e.*, j.* FROM employees e " +
                "JOIN jobs j ON e.job_id = j.id " +
                "WHERE e.id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Employee employee = extractEmployeeFromResultSet(resultSet);
                Job job = extractJobFromResultSet(resultSet);
                employeeJobMap.put(employee, job);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return employeeJobMap.isEmpty() ? null : employeeJobMap;
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT e.* FROM employees e " +
                "JOIN jobs j ON e.job_id = j.id " +
                "WHERE j.position = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, position);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                employees.add(extractEmployeeFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return employees;
    }

    private Employee extractEmployeeFromResultSet(ResultSet resultSet) throws SQLException {
        return new Employee(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getInt("age"),
                resultSet.getString("email"),
                resultSet.getInt("job_id")
        );
}
    private Job extractJobFromResultSet(ResultSet resultSet) throws SQLException {
        return new Job(
                resultSet.getLong("id"),
                resultSet.getString("position"),
                resultSet.getString("profession"),
                resultSet.getString("description"),
                resultSet.getInt("experience")
        );
    }
}
