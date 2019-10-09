package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;
import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.System.getProperty;

public class ServerStrategySolveSearchProblem implements IServerStrategy{
    private File tempFolder;
    private Map<Maze,String> solutionStock;
    public static int i;
    String tempDirectoryPath;

    public ServerStrategySolveSearchProblem(){
        tempDirectoryPath = getProperty("java.io.tmpdir");
        tempFolder= new File(tempDirectoryPath);
        System.out.println(tempDirectoryPath);

        solutionStock = new ConcurrentHashMap<Maze,String>();
    }
    @Override
    public synchronized void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {

            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();
            boolean Exist=false;
            Maze maze = (Maze)fromClient.readObject();

            //Check if a soulution exists for the current maze.
            for(Maze m : solutionStock.keySet()){
                if (m.equals(maze)) {
                    ObjectInputStream sol = new ObjectInputStream(new FileInputStream(solutionStock.get(m)));
                    toClient.writeObject(sol.readObject());
                    toClient.flush();
                    Exist=true;
                    break;
                }
            }//for

            if(!Exist){
                ObjectOutputStream fileSolution = new ObjectOutputStream(new FileOutputStream(tempDirectoryPath+"/solution"+i));//new file to save the solution
                String searchName="algorithms.search." + Server.Configurations.getSearchingAlgorithm();
                ASearchingAlgorithm search = new BestFirstSearch();
                try {
                    search =(ASearchingAlgorithm)Class.forName(searchName).newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                Solution solution=search.solve(new SearchableMaze(maze));
                fileSolution.writeObject(solution);
                toClient.writeObject(solution);
                toClient.flush();
                fileSolution.flush();
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
