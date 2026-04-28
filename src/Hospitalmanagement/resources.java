package Hospitalmanagement;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class resources {
    Properties prop = new Properties();
    String url;
    String username;
    String password;

    public resources() throws IOException{
        FileInputStream fis = new FileInputStream("config.properties");
        prop.load(fis);

        url = prop.getProperty("url");
        username = prop.getProperty("username");
        password = prop.getProperty("password");
    }


}
