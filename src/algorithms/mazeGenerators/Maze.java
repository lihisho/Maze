package algorithms.mazeGenerators;
//import java.util.concurrent.ThreadLocalRandom;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Random;
import java.io.IOException;

/**
 * Maze class
 */
public class Maze implements Serializable{
    private Position startPosition;
    private Position goalPosition;
    private int [][]maze;
    private int numOfRows;
    private int numOfColumns;
    // private Positions [][] visited;

    /**
     * Constructor for a new maze.
     * @param Rows - Num of rows in the maze
     * @param Columns Num of coulmns in the maze
     */
    public Maze (int Rows, int Columns){
        if (Rows < 1)
            Rows = 5;
        if (Columns < 1)
            Columns = 5;
        maze = new int [Rows][Columns];
        numOfRows = Rows;
        numOfColumns = Columns;
        initializeMaze();
    }

    /**
     * Constructor- gets a byte Array that represents the maze and create a Maze
     * @param byteMazeArray - represents the maze
     */
    public Maze(byte [] byteMazeArray)
    {
        int i = 0;
        byte [] column=new byte[4];
        for(int j=0 ; j < column.length; j++){
            column[j] = byteMazeArray[i];
            i++;
        }
        byte [] startY=new byte[4];
        for(int j=0 ; j < startY.length; j++){
            startY[j] = byteMazeArray[i];
            i++;
        }
        byte [] GoalY=new byte[4];
        for(int j=0 ; j < GoalY.length; j++){
            GoalY[j] = byteMazeArray[i];
            i++;
        }
        int Columns = ByteBuffer.wrap(column).getInt();
        int Rows = (byteMazeArray.length - 12)/Columns; // calculate the number of rows using number of columns.

        startPosition = new Position(0,ByteBuffer.wrap(startY).getInt());
        goalPosition = new Position(Rows -1,ByteBuffer.wrap(GoalY).getInt());
        if (Rows < 1)
            Rows = 5;
        if (Columns < 1)
            Columns = 5;
        maze = new int [Rows][Columns];
        numOfRows = Rows;
        numOfColumns = Columns;

        for (int r=0;r<Rows; r++){
            for(int c=0; c<Columns; c++){
                maze[r][c] = byteMazeArray[i];
                i++;
            }
        }

    }

    /**
     * Initialize start\end points of the maze.
     * Initialize the maze to be only walls. (Will change after Generation).
     */
    private void initializeMaze (){
        startPosition = new Position(0,0);
        goalPosition = new Position(numOfRows-1, numOfColumns-1);

        for (int i= 0; i< numOfRows ; i++)
            for (int j= 0; j< numOfColumns ; j++)
                maze[i][j] = 1;
    }

/**
 * Getters and setters for start and goal points in the maze.
 */
    public Position getStartPosition(){
    return startPosition;
}

    public Position getGoalPosition(){
        return goalPosition;
    }
    public void setStartPosition(Position s){
        startPosition=s;
    }
    public void setGoalPosition(Position g){
        goalPosition=g;
    }

    public Position getPosition (int i, int j){
        return new Position(i,j);
    }
    public int getRows (){
        return numOfRows;
    }
    public int getColumns (){
        return numOfColumns;
    }

    /**
     * Prints the whole maze.
     * S- StartingPoint.
     * E- GoalPoint.
     */
    public void print(){
        for (int i=0; i<numOfRows; i++){
            System.out.print("{");
            for (int j=0; j<numOfColumns; j++){
                if (i == startPosition.getRowIndex() && j == startPosition.getColumnIndex())
                    System.out.print("S");
                else if (i == goalPosition.getRowIndex() && j ==goalPosition.getColumnIndex())
                    System.out.print("E");
                else
                    System.out.print(maze[i][j]);
                if (j!= numOfColumns-1)
                    System.out.print(",");
            }
            System.out.println("}");

        }
       // System.out.println("start-"+maze[startPosition.getRowIndex()][startPosition.getColumnIndex()]);
        //System.out.println("end-"+ maze[goalPosition.getRowIndex()][goalPosition.getColumnIndex()]);
    }

   /* @Override
    public String toString() {
        for(int i=0; i< numOfRows; i++){
            for(int j=0;j<numOfColumns;j++){

            }
        }
    }*/

    /**
     * Return if cell is in bounderies and is not a wall.
     * @param i - Row number
     * @param j - Column number
     * @return True if the cell is valid. False if not.
     */
    public boolean isValidCell (int i , int j){
        if(i<numOfRows && i>=0 && j<numOfColumns && j>=0)
            if(maze[i][j] == 0)
                return true;
        return false;
    }

    /**
     * Return true if the cell is a wall and in bounderies.
     * @param i - Row number
     * @param j - Column number
     * @return
     */
    public boolean isValidForGenerate(int i , int j){
        if(i<numOfRows && i>=0 && j<numOfColumns && j>=0)
            if(maze[i][j] == 1)
                return true;
        return false;
    }

    /**
     * setZero's in given position.
     *
     * @param pos
     */
    public void setPath (Position pos){
        if(pos.getRowIndex()<numOfRows && pos.getRowIndex()>=0 && pos.getColumnIndex()<numOfColumns && pos.getColumnIndex()>=0) // Not necessary, will always check first if cell is valid.
            maze[pos.getRowIndex()][pos.getColumnIndex()] = 0;
    }

    public void setRandomZeros(){
        Random num =new Random();
        for (int i= 0; i< numOfRows ; i++)
            for (int j= 0; j< numOfColumns ; j++) {
               // int direction = ThreadLocalRandom.current().nextInt(0, 2); // includes 0 and does not include the last number.
                int direction = num.nextInt(2);
                if(direction == 0)
                    maze[i][j] = 0;
            }
    }

    public byte[] toByteArray(){
        int size = 0;
        size = numOfRows * numOfColumns + 12; //size of the maze + 16 bytes for start/goal position and number of rows and columns
        byte[] columns = ByteBuffer.allocate(4).putInt(numOfColumns).array();
        byte[] startY = ByteBuffer.allocate(4).putInt(startPosition.getColumnIndex()).array();
        byte[] goalY = ByteBuffer.allocate(4).putInt(goalPosition.getColumnIndex()).array();
        int p=0;

        byte [] byteArray = new byte [size] ;

        for(int j=0;j<columns.length;j++){
            byteArray[p]=columns[j];
            p++;
        }
        for (int j=0;j<startY.length;j++){
            byteArray[p]=startY[j];
            p++;
        }
        for (int j=0;j<goalY.length;j++){
            byteArray[p]=goalY[j];
            p++;
        }
        for(int i=p;i<byteArray.length;i++){ //copy all maze cells to the byteArray
            for (int j=0;j<numOfRows;j++)
                for (int k=0;k<numOfColumns;k++) {
                    byteArray[i] = (byte) maze[j][k];
                    i++;
                }
        }
        return byteArray;
    }

    public void writeObject(ObjectOutputStream stream) throws IOException{
        stream.write(this.toByteArray());

    }
    public int getCellValue(int i,int j){
        return maze[i][j];
    }

    public boolean equals(Object obj) {
        if( obj == this)
            return true;
        if(obj != null && obj.getClass() == getClass()) {
            Maze m = (Maze) obj;
            if (m.numOfRows != this.numOfRows || m.numOfColumns != this.numOfColumns)
                return false;
            if (!goalPosition.equals(m.getGoalPosition()) || !startPosition.equals(m.getStartPosition()))
                return false;
            for (int i = 0; i < numOfRows; i++)
                for (int j = 0; j < numOfColumns; j++)
                    if (maze[i][j] != m.getCellValue(i, j))
                        return false;
            return true;
        }
        return false;
    }
}