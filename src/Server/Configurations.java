package Server;


import algorithms.search.BestFirstSearch;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class Configurations {
    public static void main(String [] args){
        Properties prop = new Properties();
        OutputStream output=null;

        try{
            output = new FileOutputStream("resources/config.properties");

            //set properties
            prop.setProperty("searchingAlgorithm", "BestFirstSearch");
            prop.setProperty("generateAlgorithm", "MyMazeGenerator");
            prop.setProperty("numOfThreads","2");


            prop.store(output,null);
            output.close();

        }
        catch (IOException io){
            io.printStackTrace();
        }
    }

}
