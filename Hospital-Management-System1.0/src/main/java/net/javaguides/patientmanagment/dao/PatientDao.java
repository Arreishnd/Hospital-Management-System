package net.javaguides.patientmanagment.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.javaguides.patientmanagment.model.Patient;

//this dao class provides CRUD database operations for the table patient in the db
public class PatientDao {
	
	private String jdbcURL = "jdbc:mysql://localhost:3306/patient?useSSL=false"; //will it work???
	private String jdbcUsername = "root";
	private String jdbcPassword = "csci4101"; //should i change it to root from my server?
	
	private static final String INSERT_PATIENTS_SQL = "INSERT INTO Patient" + "  (fistname, lastname, age) VALUES "
			+ " (?, ?, ?);";

	private static final String SELECT_PATIENT_BY_ID = "select id, fistname, lastname, age from Patient where id =?";
	private static final String SELECT_ALL_PATIENTS = "select * from Patient";
	private static final String DELETE_PATIENTS_SQL = "delete from Patient where id = ?;";
	private static final String UPDATE_PATIENTS_SQL = "update Patient set firstname = ?,lastname= ?, age =? where id = ?;";

	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	//create or insert patient
	public void insertPatient(Patient patient) throws SQLException{
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PATIENTS_SQL)){
			preparedStatement.setString(1, patient.getFirstName());
			preparedStatement.setString(2, patient.getLastName());
			preparedStatement.setInt(3, patient.getAge());
			preparedStatement.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//update patient
		public void updatePatient(Patient patient) throws SQLException{
			boolean rowUpdated;
			try(Connection connection = getConnection();
					PreparedStatement statement = connection.prepareStatement(UPDATE_PATIENTS_SQL)){
				statement.setString(1, patient.getFirstName());
				statement.setString(2, patient.getLastName());
				statement.setInt(3, patient.getAge());
				statement.setInt(4, patient.getId());
				
				rowUpdated = statement.executeUpdate() > 0;
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	//select patient by id
		public Patient selectPatient(int id) {
			Patient patient = null;
			try(Connection connection = getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PATIENT_BY_ID)){
				preparedStatement.setInt(1, id);
				System.out.println(preparedStatement);
				
				ResultSet rs = preparedStatement.executeQuery();
				
				while(rs.next()) {
					String firstname= rs.getString("FirstName");
					String lastname= rs.getString("LastName");
					int age= rs.getInt("Age");
					patient = new Patient(id,firstname,lastname,age);
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
			return patient;
		}

	//select all patients
		public List<Patient> selectAllPatients() {
			List<Patient> patients = new ArrayList<>();
			try(Connection connection = getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PATIENTS)){
				System.out.println(preparedStatement);
				
				ResultSet rs = preparedStatement.executeQuery();
				
				while(rs.next()) {
					int id= rs.getInt("Id");
					String firstname= rs.getString("FirstName");
					String lastname= rs.getString("LastName");
					int age= rs.getInt("Age");
					patients.add(new Patient(id,firstname,lastname,age));
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
			return patients;
		}
		
	//delete patient
		public boolean deletPatient(int id) throws SQLException{
			boolean rowDeleted;
			try(Connection connection = getConnection();
					PreparedStatement statement = connection.prepareStatement(DELETE_PATIENTS_SQL)){
				statement.setInt(1,id);
				
				rowDeleted = statement.executeUpdate() > 0;
			}
			return rowDeleted;
		}
}
