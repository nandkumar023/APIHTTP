package Base;

import java.io.*;
import java.util.*;

public class TestBase {
public Properties prop;

    public TestBase(){
        try {
            prop =new Properties();
            FileInputStream ip=new FileInputStream(System.getProperty("user.dir")+"/src/main/java/Configuration/config.properties");
           prop.load(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
