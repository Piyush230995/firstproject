package Hospitalmanagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagement {
    public static void main(String[] args) throws IOException {
        resources res = new resources();
        String url = res.url;
        String username = res.username;
        String password = res.password;

        try{
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try{
            Connection con = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(con, scanner);
            Doctor doctor = new Doctor(con);
            Appointments appointments = new Appointments(patient, doctor, con, scanner);

            while(true){
                System.out.println();
                System.out.println();
                System.out.println("HOSPITAL MANAGEMENT SYSTEM ");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctor");
                System.out.println("4. Book Appointment");
                System.out.println("5. View Doctor's Appointments");
                System.out.println("6. Exit");
                System.out.print("\nEnter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        patient.addPatient();
                        break;
                    case 2:
                        patient.viewPatients();
                        break;
                    case 3:
                        doctor.viewDoctors();
                        break;
                    case 4:
                        appointments.bookAppointment();
                        break;
                    case 5:
                        appointments.viewAppointments();
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("\nInvalid choice");
                        break;
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
