package Server;
import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import java.io.*;


public class ServerStrategyGenerateMaze implements IServerStrategy {

    @Override
    public synchronized void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {

            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            ByteArrayOutputStream compressorOut=new ByteArrayOutputStream();
            MyCompressorOutputStream compressorOutputStream = new MyCompressorOutputStream(compressorOut);

            int [] mazeDimension = (int[])fromClient.readObject();//need to check if not null???
            if(mazeDimension != null){

                String mazeName="algorithms.mazeGenerators." + Server.Configurations.getGenerateAlgorithm();
                AMazeGenerator mazeGen = new MyMazeGenerator();
                try {
                    mazeGen =(AMazeGenerator)Class.forName(mazeName).newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                Maze maze = mazeGen.generate(mazeDimension[0],mazeDimension[1]);

                //MyCompressorOutputStream compressorOutputStream = new MyCompressorOutputStream(toClient);
                compressorOutputStream.write(maze.toByteArray());
                toClient.writeObject(compressorOut.toByteArray());
                toClient.flush();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}