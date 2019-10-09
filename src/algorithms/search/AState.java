package algorithms.search;

import algorithms.mazeGenerators.Position;

public abstract class AState {

    protected int cost;
    protected AState comeFrom;


    AState(){
        cost=0;
        comeFrom=null;
    }

    @Override
    public abstract boolean equals(Object obj) ;
    public abstract String toString();
    public abstract void setCost(int cost);
    public abstract int hashCode();
}

