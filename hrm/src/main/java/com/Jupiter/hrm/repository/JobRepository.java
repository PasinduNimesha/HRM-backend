package com.Jupiter.hrm.repository;
import com.Jupiter.hrm.entity.Job;
import com.Jupiter.hrm.config.DbConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobRepository {
    Connection connection;
    public JobRepository() {
        try {
            connection = DbConfig.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean save(Job job){
        try{
            String sqlQuery = "INSERT INTO job (job_title, pay_grade_id, department_id) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, job.getJob_title());
            preparedStatement.setString(2, job.getPay_grade_id());
            preparedStatement.setString(3, job.getDepartment_id());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean delete(int job_id){
        try{
            String sqlQuery = "DELETE FROM job WHERE job_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, job_id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean update(Job job){
        try{
            String sqlQuery = "UPDATE job SET job_title = ?, pay_grade_id = ?, department_id = ? WHERE job_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, job.getJob_title());
            preparedStatement.setString(2, job.getPay_grade_id());
            preparedStatement.setString(3, job.getDepartment_id());
            preparedStatement.setInt(4, job.getJob_id());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Integer> getJobIds(){
        try{
            String sqlQuery = "SELECT job_id FROM job";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Integer> jobIds = new ArrayList<>();
            while (resultSet.next()) {
                jobIds.add(resultSet.getInt("job_id"));
            }
            return jobIds;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
