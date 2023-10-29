package com.Jupiter.hrm.repository;

import com.Jupiter.hrm.dto.DepartmentGroup;
import com.Jupiter.hrm.dto.JobTitleGroup;
import com.Jupiter.hrm.dto.PayGradeGroup;
import com.Jupiter.hrm.entity.Employee;
import com.Jupiter.hrm.config.DbConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeRepo {
    Connection connection;
    public EmployeeRepo() {
        try {
            connection = DbConfig.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Long save(Employee employee){
        try{
            String sqlQuery = "INSERT INTO employee (name, birthdate, marital_status, emergency_contact_id, job_id, gender, address, employment_status_id, supervisor, branch_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getBirthdate());
            preparedStatement.setString(3, employee.getMarital_status());
            preparedStatement.setInt(4, employee.getEmergency_contact_id());
            preparedStatement.setInt(5, employee.getJob_id());
            preparedStatement.setString(6, employee.getGender());
            preparedStatement.setString(7, employee.getAddress());
            preparedStatement.setInt(8, employee.getEmployment_status_id());
            preparedStatement.setInt(9, employee.getSupervisor());
            preparedStatement.setInt(10, employee.getBranch_id());
            int rowsInserted = preparedStatement.executeUpdate();


            if (rowsInserted > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long lastInsertID = generatedKeys.getLong(1);
                    System.out.println("Last Inserted ID: " + lastInsertID);
                    return lastInsertID;
                } else {
                    System.out.println("Failed to retrieve last inserted ID.");
                }
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Employee> findByDepartment(int department_id){
        List<Employee> employeeList = new ArrayList<>();
        try{
            String sqlQuery = "SELECT * FROM employee WHERE department_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, department_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employeeList.add(getEmployee(new Employee(), resultSet));
            }
            return employeeList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Employee> findAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();

        try {
            String sqlQuery = "SELECT * FROM employee";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                employeeList.add(getEmployee(new Employee(), resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeList;
    }



    public Optional<Employee> findById(int employeeID){
        Employee employee = null;

        try {
            String sqlQuery = "SELECT * FROM employee WHERE employee_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            System.out.println(employeeID);
            preparedStatement.setLong(1, employeeID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                employee = getEmployee(new Employee(), resultSet);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(employee);
    }

    public void delete(Employee employee){
        try {
            String sqlQuery = "DELETE FROM employee WHERE employee_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, employee.getEmployee_id());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Employee getEmployee(Employee employee, ResultSet resultSet){
        try {
            employee.setEmployee_id(resultSet.getInt("employee_id"));
            employee.setName(resultSet.getString("name"));
            employee.setBirthdate(resultSet.getString("birthdate"));
            employee.setEmergency_contact_id(resultSet.getInt("emergency_contact_id"));
            employee.setMarital_status(resultSet.getString("marital_status"));
            employee.setJob_id(resultSet.getInt("job_id"));
            employee.setGender(resultSet.getString("gender"));
            employee.setAddress(resultSet.getString("address"));
            employee.setEmployment_status_id(resultSet.getInt("employment_status_id"));
            employee.setSupervisor(resultSet.getInt("supervisor"));
            employee.setBranch_id(resultSet.getInt("branch_id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employee;
    }

    public void update(Employee employee) {
        try {
            String sqlQuery = "UPDATE employee SET name = ?, birthdate = ?, marital_status = ?, emergency_contact_id = ?, job_id = ?, gender = ?, address = ?, employment_status_id = ?, supervisor = ?, branch_id = ? where employee_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getBirthdate());
            preparedStatement.setString(3, employee.getMarital_status());
            preparedStatement.setInt(4, employee.getEmergency_contact_id());
            preparedStatement.setInt(5, employee.getJob_id());
            preparedStatement.setString(6, employee.getGender());
            preparedStatement.setString(7, employee.getAddress());
            preparedStatement.setInt(8, employee.getEmployment_status_id());
            preparedStatement.setInt(9, employee.getSupervisor());
            preparedStatement.setInt(10, employee.getBranch_id());
            preparedStatement.setInt(11, employee.getEmployee_id());
            System.out.println("id :" + employee.getEmployee_id());
            preparedStatement.executeUpdate();
            int rowsUpdated = preparedStatement.executeUpdate();

            System.out.println(rowsUpdated);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Integer> getEmployeeIds(){
        try{
            String sqlQuery = "SELECT employee_id FROM employee";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Integer> employeeIds = new ArrayList<>();
            while (resultSet.next()) {
                employeeIds.add(resultSet.getInt("employee_id"));
            }
            return employeeIds;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<JobTitleGroup> findGroupByJobTitle() {
        try {
            String sqlQuery = "select j.job_title as job_title, count(*) as count from employee e join job j on j.job_id = e.job_id group by j.job_id;";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<JobTitleGroup> jobTitleGroupList = new ArrayList<>();
            while (resultSet.next()) {
                JobTitleGroup jobTitleGroup = new JobTitleGroup();
                jobTitleGroup.setJob_title(resultSet.getString("job_title"));
                jobTitleGroup.setNumber_of_employees(resultSet.getInt("count"));
                jobTitleGroupList.add(jobTitleGroup);
            }
            return jobTitleGroupList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PayGradeGroup> findGroupByPayGrade() {
        try {
            String sqlQuery = "select p.level as pay_grade, count(*) as count from employee e join job j on j.job_id = e.job_id join pay_grade p on p.pay_grade_id = j.pay_grade_id group by p.pay_grade_id;";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<PayGradeGroup> payGradeGroupList = new ArrayList<>();
            while (resultSet.next()) {
                PayGradeGroup payGradeGroup = new PayGradeGroup();
                payGradeGroup.setPay_grade(resultSet.getString("pay_grade"));
                payGradeGroup.setNumber_of_employees(resultSet.getInt("count"));
                payGradeGroupList.add(payGradeGroup);
            }
            return payGradeGroupList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DepartmentGroup> findGroupByDepartment() {
        try {
            String sqlQuery = "select d.name as department_name, count(*) as count from employee e join job j on j.job_id = e.job_id join department d on d.department_id = j.department_id group by d.department_id;";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<DepartmentGroup> departmentGroupList = new ArrayList<>();
            while (resultSet.next()) {
                DepartmentGroup departmentGroup = new DepartmentGroup();
                departmentGroup.setDepartment_name(resultSet.getString("department_name"));
                departmentGroup.setNumber_of_employees(resultSet.getInt("count"));
                departmentGroupList.add(departmentGroup);

            }
            return departmentGroupList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
