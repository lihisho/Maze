package algorithms.mazeGenerators;

import java.io.Serializable;

public class Position implements Serializable {
    private int i;
    private int j;

    /**
     * constructor for Position in maze
     * @param i- row
     * @param j-column
     */
    Position(int i,int j){
        this.i = i;
        this.j = j;
    }

    public String toString(){
        return ("{" + i + "," + j + "}" );
    }
    /**
     * Getters and setters for positions in the maze.
     */
    public int getRowIndex(){
        return i;
    }

    public int getColumnIndex(){
        return j;
    }

    @Override
    public boolean equals(Object obj) {
        if( obj == this)
            return true;
        if(obj != null && obj.getClass() == getClass()) {
            Position position = (Position) obj;
            if (position.i == this.i && position.j == this.j)
                return true;
        }
        return false;

    }

    public int hashCode(){
        return 13 * i + 7 *j;
    }


}
