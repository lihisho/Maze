package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.io.Serializable;

public class MazeState extends AState implements Serializable{

    private Position position;

    /**
     * Constructor for maze state.
     * @param p - state position
     * @param comeFrom - previous state.
     * @param cost - the cost from the start state until the current state.
     */
    MazeState(Position p,AState comeFrom, int cost){
        position=p;
        this.comeFrom=comeFrom;
        this.cost=cost;
    }

    /**
     * getters and setters
     */
    public Position getPosition(){
        return position;
    }

    public int getCost(){
        return cost;
    }

    public void setPosition(Position p){
        position=p;
    }

    public void setCost(int cost){
        this.cost=cost;
    }

    public void setComeFrom(AState comeFrom){
        this.comeFrom=comeFrom;
    }

    @Override
    public String toString() {
        return (position.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)
            return true;
        if(obj==null||getClass()!=obj.getClass())
            return false;
        MazeState state= (MazeState) obj;

        if(state!=null)
            return state.getPosition().equals(position);
        return false;
    }


    @Override
    public int hashCode(){
        return position !=null ? position.hashCode() : 0;
    }

    public int compareTo(MazeState other){
        if (this.getCost()>other.getCost())
            return 1;
        if(this.getCost()==other.getCost())
            return 0;
        return -1;
    }


}
