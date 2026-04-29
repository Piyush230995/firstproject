package Hospitalmanagement;

import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class Appointments {
    Patient patient;
    Doctor doctor;
    Connection connection;
    Scanner scanner;

    public  Appointments(Patient patient, Doctor doctor, Connection connection, Scanner scanner){
        this.patient = patient;
        this.doctor = doctor;
        this.connection = connection;
        this.scanner = scanner;
    }

    public void bookAppointment(){
        System.out.println("Enter patient ID");
        int patientID = scanner.nextInt();
        System.out.println("Enter doctor ID");
        int doctorID = scanner.nextInt();
        System.out.println("Enter appointment date (YYYY-MM-DD)");
        String stringDate = scanner.next();
        java.sql.Date date = java.sql.Date.valueOf(stringDate);

        boolean patientExists = patient.getPatientById(patientID);
        boolean doctorExists = doctor.getDoctorById(doctorID);

        if(!patientExists && doctorExists){
            System.out.println("No patient with the patient ID exists");
        } else if (patientExists && !doctorExists) {
            System.out.println("No doctor with the doctor ID exists");
        } else if (patientExists && doctorExists) {
                    if(checkDoctorAvailability(doctorID, date, connection)){

                        String query = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES (?,?,?)";
                        try{
                            PreparedStatement ps = connection.prepareStatement(query);
                            ps.setInt(1, patientID);
                            ps.setInt(2, doctorID);
                            ps.setDate(3, date);
                            int affectedRows = ps.executeUpdate();
                            if(affectedRows > 0){
                                System.out.println("\nAppointment has been booked successfully");
                            }else {
                                System.out.println("\nAppointment could not be booked");
                            }

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }else  {
                        System.out.println("\nDoctor already booked for the day");
                    }



        } else {
            System.out.println("\nPatient ID and doctor ID do not match");
        }

    }

    public boolean checkDoctorAvailability(int doctorID, java.sql.Date date, Connection connection){
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";

        try{
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, doctorID);
            ps.setDate(2, date);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int count = rs.getInt(1);
                return count == 0;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
            return false;
    }


    public void viewAppointments(){
        System.out.println("Enter doctor ID");
        int doctorID = scanner.nextInt();

        String query =  "SELECT * FROM appointments WHERE doctor_id = ?;";
        try{
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, doctorID);
            ResultSet rs = ps.executeQuery();
            System.out.println("-----+------------+------------+--------------------");
            System.out.println(" id  | patient_id | doctor_id  | appointment_date  ");
            System.out.println("-----+------------+------------+--------------------");
            while(rs.next()){
                int id = rs.getInt("id");
                int patient_id = rs.getInt("patient_id");
                int doctor_id = rs.getInt("doctor_id");
                java.sql.Date appointment_date = rs.getDate("appointment_date");

                System.out.printf("|%-5d|%-12d|%-12d|%-20s|%n", id, patient_id, doctor_id, appointment_date);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

}
