import java.sql.Struct;
import java.util.ArrayList;

public class GraphConverter
{
    Grid grid;

    Boolean[][] nodes;
    Boolean[][] prevSeen;
    Graph graph;
    int gridHeight;
    int gridWidth;
    Cell origin;
    Vertex originVertex;
    Vertex endVertex;

    enum Directions {NORTH,EAST,SOUTH,WEST}



    public GraphConverter(Grid grid){
        this.grid = new Grid(grid);

        this.graph = new Graph();

        nodes = new Boolean[grid.getGridWidth()][grid.getGridHeight()];
        prevSeen = new Boolean[grid.getGridWidth()][grid.getGridHeight()];
        for (int i = 0; i < grid.getGridWidth() ; i++) {
            for (int j = 0; j < grid.getGridHeight() ; j++) {
                nodes[i][j] = false;
                prevSeen[i][j] = false;
            }
        }

        this.gridHeight = grid.getGridHeight();
        this.gridWidth = grid.getGridWidth();

        this.origin = grid.getCell(1,1);
        nodes[1][1] = true;

        originVertex = new Vertex(this.origin);
        graph.addVertex(originVertex);

        convert();
        findChildren(originVertex,originVertex);
        prevSeenRemover();


    }

    public void prevSeenRemover(){

        for (int i = 0; i < grid.getGridWidth() ; i++) {
            for (int j = 0; j < grid.getGridHeight() ; j++) {
                if(prevSeen[i][j] == true){
                    grid.getCell(i,j).setAlive(false);
                }
            }
        }

    }

    public void convert(){

        for (int i = 1; i < gridWidth ; i++) {
            for (int j = 1; j < gridHeight ; j++) {
                Cell temp = grid.getCell(i,j);
                if(!temp.isAlive()){
                    if(isNode(i,j)){
                        nodes[i][j] = true;
                    }
                }

            }
        }
    }

    public void findChildren(Vertex parent,Vertex newCell){
        Vertex currentParent = parent;
        int x = newCell.label.getX();
        int y = newCell.label.getY();
//        System.out.println(x + " " + y);
//        System.out.println(" PARENT: " + parent.label.getX() + " " + parent.label.getY());
        if(isNode(x,y) && parent != newCell){
            graph.addVertex(newCell);
            graph.addVertex(parent);

            graph.addEdge(parent,newCell);
            if(newCell.label.getX() == gridWidth - 1 && newCell.label.getY() == gridHeight - 1) endVertex = newCell;
            currentParent = newCell;
        }
        prevSeen[x][y] = true;
        newCell.label.setAlive(true);
        ArrayList<Directions> dir = directionsAvailable(x,y);
        if(dir.isEmpty()) return;

        for (Directions d:dir) {
            if(d == Directions.NORTH) findChildren(currentParent, new Vertex(grid.getCell(x,y-1)));
            if(d == Directions.EAST) findChildren(currentParent, new Vertex(grid.getCell(x+1,y)) );
            if(d == Directions.SOUTH) findChildren(currentParent, new Vertex(grid.getCell(x,y+1)) );
            if(d == Directions.WEST) findChildren(currentParent,new Vertex(grid.getCell(x-1,y)) );
        }

    }

    public ArrayList<Directions> directionsAvailable(int x, int y){
        ArrayList<Directions> directions = new ArrayList<>();
        if(!checkUp(x,y)) directions.add(Directions.NORTH);
        if(!checkRight(x,y)) directions.add(Directions.EAST);
        if(!checkDown(x,y)) directions.add(Directions.SOUTH);
        if(!checkLeft(x,y)) directions.add(Directions.WEST);
        return directions;

    }

    //check for node point
    //only 1 direction
    //more than 2 directions
    //so check there are not only 2 directions

    public boolean isNode(int x, int y){
        if(x == gridWidth - 1 && y == gridHeight - 1) return true;
        int counter = 0;
        if(!checkUp(x,y)) counter+=2;
        if(!checkRight(x,y)) counter+=1;
        if(!checkDown(x,y)) counter+=2;
        if(!checkLeft(x,y)) counter+=1;
        if(counter > 4) return true;
        if(counter % 2 != 0) return true;
        else return false;
    }

    public boolean checkUp(int x, int y){
        //if(y == 1) return false;
        if(!grid.getCell(x, y - 1).isAlive()) return false;
        else return true;
    };
    public boolean checkDown(int x, int y){
        //if(y == gridHeight - 2  || y == gridHeight - 1) return false;
        if(!grid.getCell(x, y + 1).isAlive()) return false;
        else return true;
    };
    public boolean checkLeft(int x, int y){
        //if(x == 1) return false;
        if(!grid.getCell(x - 1 , y).isAlive()) return false;
        else return true;
    };
    public boolean checkRight(int x, int y){
        //if(x == gridWidth - 2 || x == gridWidth - 1) return false;
        if(!grid.getCell(x + 1, y).isAlive()) return false;
        else return true;
    };




}
