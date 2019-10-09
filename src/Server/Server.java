package Server;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.*;


public class Server {
    private int portNum;
    private int listeningInterval;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;

    public Server(int portNum, int listeningInterval, IServerStrategy serverStrategy) {
        this.portNum = portNum;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
        Configurations.setProperties();
    }

    public void start() {
        new Thread(() -> {
            runServer();
        }).start();
    }

    private void runServer()  {
        try {
            //Properties prop = new Properties();
            //prop.load(new FileInputStream("Resources/config.properties"));
            ServerSocket server = new ServerSocket(portNum);
            server.setSoTimeout(listeningInterval);
            System.out.println("starting..");
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
            threadPoolExecutor.setCorePoolSize(Runtime.getRuntime().availableProcessors() * Configurations.getNumOfThreads());

            while (!stop) {
                try {
                    Socket clientSocket = server.accept(); // blocking call

                    threadPoolExecutor.execute(new Thread(()->handleClient(clientSocket)));


                } catch (SocketTimeoutException e) {
                    // e.printStackTrace();
                    // System.out.println("SocketTimeout - No clients pending!");
                }
            }
            threadPoolExecutor.shutdown();
            server.close();

        } catch (IOException e) {
            //LOG.error("IOException", e);
        }
    }


    /*private void runServer() {
        try {
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(listeningInterval);
            LOG.info(String.format("Server started! (port: %s)", port));
            while (!stop) {
                try {
                    Socket clientSocket = server.accept(); // blocking call
                    LOG.info(String.format("Client excepted: %s", clientSocket.toString()));
                    new Thread(() -> {
                        handleClient(clientSocket);
                    }).start();
                } catch (SocketTimeoutException e) {
                    LOG.debug("SocketTimeout - No clients pending!");
                }
            }
            server.close();
        } catch (IOException e) {
            LOG.error("IOException", e);
        }
    }*/

    private void handleClient(Socket clientSocket) {
        try {
            //LOG.debug("Client excepted!");
            // LOG.debug(String.format("Handling client with socket: %s", clientSocket.toString()));
            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.getInputStream().close();
            clientSocket.getOutputStream().close();
            clientSocket.close();
        } catch (IOException e) {
            // LOG.error("IOException", e);
        }
    }

    public void stop() {
        stop = true;
    }

    // This class is used for configurations.
    public  static class  Configurations {
        private static Properties prop;

        public static void setProperties () {

            prop= new Properties();
            //OutputStream output = null;
            InputStream in = null;
            try{
                in = new FileInputStream("Resources/config.properties");
                prop.load(in);
            }
            catch (IOException io) {
                io.printStackTrace();
            }
            finally {
                if (in != null){
                    try {
                        in.close();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        // returns searching algorithm.
        public static String getSearchingAlgorithm(){
            String search =  prop.getProperty("searchingAlgorithm");
            if (search != "BestFirstSearch" ||  search != "DepthFirstSearch" || search != "BreadthFirstSearch" ){
                search = "BestFirstSearch";
            }
            return search;
        }
        // returns generating algorithm.
        public static String getGenerateAlgorithm(){
            String genAlgorithm =  prop.getProperty("generateAlgorithm");
            if (genAlgorithm != "MyMazeGenerator" ||  genAlgorithm != "SimpleMazeGenerator" ){
                genAlgorithm = "MyMazeGenerator";
            }
            return genAlgorithm;
        }
        // Returns the number of threads in the thread pool.
        public static int getNumOfThreads (){

            try{
                int num = Integer.parseInt(prop.getProperty("numOfThreads"));
                if (num > 0)
                    return num;

            }
            catch (NumberFormatException ex){
                System.out.println( ex.getMessage()); //print the error message.
            }
            return 2; // num is the defualt number of threads in the thread pool.
        }
    }

}

