package com.Jupiter.hrm.repository;

import com.Jupiter.hrm.config.DbConfig;
import com.Jupiter.hrm.dto.DepartmentDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DepartmentRepo {
    Connection connection;

    public DepartmentRepo() {
        try {
            connection = DbConfig.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean save(DepartmentDto departmentDto){
        try{
            String sqlQuery = "INSERT INTO department (name, building, manager) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, departmentDto.getName());
            preparedStatement.setString(2, departmentDto.getBuilding());
            preparedStatement.setString(3, departmentDto.getManager());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean delete(int department_id){
        try{
            String sqlQuery = "DELETE FROM department WHERE department_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, department_id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean update(DepartmentDto departmentDto){
        try{
            String sqlQuery = "UPDATE department SET name = ?, building = ?, manager = ? WHERE department_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, departmentDto.getName());
            preparedStatement.setString(2, departmentDto.getBuilding());
            preparedStatement.setString(3, departmentDto.getManager());
            preparedStatement.setInt(4, departmentDto.getDepartment_id());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
