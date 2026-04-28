package Hospitalmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient{

    Connection connection;
    Scanner scanner;

    public Patient(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }
    public void addPatient(){
        System.out.println("Enter Patient Name : ");
        String name = scanner.next();
        System.out.println("Enter Patient Age : ");
        int age = scanner.nextInt();
        System.out.println("Enter Patient Gender : ");
        String gender = scanner.next();

        try{
            String query = "INSERT INTO patients(name, age, gender) VALUES (?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            int affectedRows = ps.executeUpdate();
            if(affectedRows>0){
                System.out.println("Patient added successfully");
            }else{
                System.out.println("Failed to add patient");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void viewPatients(){

    }

}
