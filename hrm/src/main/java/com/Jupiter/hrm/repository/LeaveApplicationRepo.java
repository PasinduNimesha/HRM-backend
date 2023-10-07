package com.Jupiter.hrm.repository;

import com.Jupiter.hrm.config.DbConfig;
import com.Jupiter.hrm.entity.LeaveApplication;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            String query = "UPDATE leave_application SET employee_id = ?, leave_type_id = ?, start_date = ?, end_date = ?, status = ? WHERE application_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, leaveApplication.getEmployee_id());
            preparedStatement.setLong(2, leaveApplication.getLeave_type_id());
            preparedStatement.setString(3, leaveApplication.getStart_date());
            preparedStatement.setString(4, leaveApplication.getEnd_date());
            preparedStatement.setString(5, leaveApplication.getStatus());
            preparedStatement.setLong(6, leaveApplication.getApplication_id());
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
            preparedStatement.setString(3, leaveApplication.getStart_date());
            preparedStatement.setString(4, leaveApplication.getEnd_date());
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
