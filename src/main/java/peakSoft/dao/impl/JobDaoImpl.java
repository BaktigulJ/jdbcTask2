package peakSoft.dao.impl;

import peakSoft.config.JdbcConf;
import peakSoft.dao.JobDao;
import peakSoft.model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDaoImpl implements JobDao {

    private final Connection connection = JdbcConf.getConnection();

    @Override
    public void createJobTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("""
create table if not exists jobs(
id serial primary key,
position varchar not null ,
profession varchar not null ,  
description varchar(250),
experience int
);
""");
statement.close();
}catch (SQLException e){
    System.out.println(e.getMessage());
}
    }

    @Override
    public void addJob(Job job) {
        String query = "INSERT INTO jobs (position, profession, description, experience) VALUES (?, ?, ?, ?)";

try(PreparedStatement preparedStatement= connection.prepareStatement(query)){
    preparedStatement.setString(1, job.getPosition());
    preparedStatement.setString(2, job.getProfession());
    preparedStatement.setString(3, job.getDescription());
    preparedStatement.setInt(4, job.getExperience());
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
    public Job getJobById(Long jobId) {
        String query= "select * from jobs where id = ?";
        try(PreparedStatement preparedStatement= connection.prepareStatement(query)){
            preparedStatement.setLong(1, jobId);
            ResultSet resultSet= preparedStatement.executeQuery();

            if (resultSet.next()) {
                return extractJobFromResultSet(resultSet);
            }
        }catch (SQLException e ){
            System.out.println(e.getMessage());
        }return null;
    }

    @Override
    public List<Job> sortByExperience(boolean ascOrDesc) {
        String order = ascOrDesc ? "ASC" : "DESC";
        String query = "SELECT * FROM jobs ORDER BY experience " + order;

        List<Job> jobs = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Job job = extractJobFromResultSet(resultSet);
                jobs.add(job);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return jobs;
    }
    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        String query = """
                select * from jobs j inner join employees e on j.id=e.job_id where e.id = ?;
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return extractJobFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
return null;
    }

    @Override
    public void deleteDescriptionColumn() {

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("alter table jobs drop column description");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
        private Job extractJobFromResultSet (ResultSet resultSet) throws SQLException {
            return new Job(
                    resultSet.getLong("id"),
                    resultSet.getString("position"),
                    resultSet.getString("profession"),
                    resultSet.getString("description"),
                    resultSet.getInt("experience")
            );
        }
    }

