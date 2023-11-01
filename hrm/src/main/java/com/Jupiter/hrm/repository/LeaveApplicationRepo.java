package com.Jupiter.hrm.repository;

import com.Jupiter.hrm.config.DbConfig;
import com.Jupiter.hrm.entity.LeaveApplication;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LeaveApplicationRepo {

    private final Connection connection;

    public LeaveApplicationRepo() {
        try {
            connection = DbConfig.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateApplication(LeaveApplication leaveApplication) {
        try {
            String query = "UPDATE leave_application SET status = ? WHERE application_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, leaveApplication.getStatus());
            preparedStatement.setLong(2, leaveApplication.getApplication_id());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteApplication(Long id) {
        try {
            String query = "DELETE FROM leave_application WHERE application_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean newApplication(LeaveApplication leaveApplication) {
        try {
            String query = "INSERT INTO leave_application (employee_id, leave_type_id, start_date, end_date, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, leaveApplication.getEmployee_id());
            preparedStatement.setLong(2, leaveApplication.getLeave_type_id());
            preparedStatement.setDate(3, java.sql.Date.valueOf(leaveApplication.getStart_date()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(leaveApplication.getEnd_date()));
            preparedStatement.setString(5, leaveApplication.getStatus());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public LeaveApplication getApplication(Long id) {
        try {
            String query = "SELECT * FROM leave_application WHERE application_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            return getLeaveApplication(new LeaveApplication(), preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<LeaveApplication> getApplicationBySupervisor(int id){
        try {
            String query = "SELECT * FROM leave_application WHERE employee_id IN (SELECT employee_id FROM employee WHERE supervisor = ?) and status = 'Pending'";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            List<LeaveApplication> leaveApplications = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (true) {
                LeaveApplication leaveApplication = new LeaveApplication();
                leaveApplication = getLeaveApplication(leaveApplication, resultSet);
                if (leaveApplication == null) {
                    break;
                }
                leaveApplications.add(leaveApplication);
            }
            return leaveApplications;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public LeaveApplication getApplicationByEmployee(int id){
        try {
            String query = "SELECT * FROM leave_application WHERE employee_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            return getLeaveApplication(new LeaveApplication(), preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    Total leaves in given period by department
    public HashMap<String, Integer> getCountByDepartment(String start_date, String end_date){
        try {
            String query = "SELECT d.name, sum(l.end_date - l.start_date) as 'total leave'  from employee e join job j on e.job_id = j.job_id join leave_application l on e.employee_id = l.employee_id join department d on d.department_id = j.department_id where l.start_date >= ? and l.end_date <= ? and l.status = 'Approved' group by j.department_id;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, start_date);
            preparedStatement.setString(2, end_date);
            ResultSet resultSet = preparedStatement.executeQuery();
            HashMap<String, Integer> map = new HashMap<>();
            while (resultSet.next()) {
                map.put(resultSet.getString("name"), resultSet.getInt("total leave"));
            }
            return map;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private LeaveApplication getLeaveApplication(LeaveApplication leaveApplication, ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                leaveApplication.setApplication_id(resultSet.getLong("application_id"));
                leaveApplication.setEmployee_id(resultSet.getLong("employee_id"));
                leaveApplication.setLeave_type_id(resultSet.getLong("leave_type_id"));
                leaveApplication.setStart_date(resultSet.getString("start_date"));
                leaveApplication.setEnd_date(resultSet.getString("end_date"));
                leaveApplication.setStatus(resultSet.getString("status"));
                return leaveApplication;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





}
