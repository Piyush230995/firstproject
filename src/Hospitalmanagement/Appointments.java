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
        System.out.println("Enter appointment date");
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
                                System.out.println("Appointment has been booked successfully");
                            }else {
                                System.out.println("Appointment could not be booked");
                            }

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }



        } else {
            System.out.println("Patient ID and doctor ID do not match");
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
}
