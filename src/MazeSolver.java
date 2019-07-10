import java.util.ArrayList;

public class MazeSolver
{
    Vertex Origin;
    Vertex End;
    Graph graph;

    //F = G + H
    //G is previous node + 1
    //H is gridwidth - 2 squared + gridheight -2 squared etc

    Grid grid;
    ArrayList<Cell> openList;
    ArrayList<Cell> closedList;
    Cell origin;
    Cell target;

    enum Directions {N,E,S,W}

    public MazeSolver(Grid grid){
        this.grid = new Grid(grid);
        this.origin = grid.getCell(1,1);
        this.target = grid.getCell(grid.getGridWidth()-2,grid.getGridHeight()-2);
        solve(this.origin,this.target);

    }

    public void solve(Cell origin, Cell target){
        openList = new ArrayList<>();
        closedList = new ArrayList<>();

        openList.add(origin);

        while (!openList.isEmpty()){


            Cell currentCell = null;
            int min = Integer.MAX_VALUE;
            for (Cell cell: openList) {
                if(cell.Fscore < min){
                    min = cell.Fscore;
                    currentCell = cell;
                }


            }

            openList.remove(currentCell);



            ArrayList<Cell> children = directionsAvailable(currentCell.getX(),currentCell.getY(),currentCell);
            currentCell.children = children;

            for (Cell child: children) {

                if(child.getX() == target.getX() && child.getY() == target.getY()){
                    this.target = child;
                    return;
                }

                child.Gscore = currentCell.Gscore + 1;
                child.Hscore = (grid.getGridWidth() - child.getX()) * (grid.getGridWidth() - child.getX()) +
                        (grid.getGridHeight() - child.getY()) * (grid.getGridHeight() - child.getY());
                child.Fscore = child.Gscore + child.Hscore;

                boolean skip = false;
                for (Cell cell: openList){

                    if(cell.getX() == child.getX() && cell.getY() == child.getY()){
                        if(cell.Fscore < child.Fscore) skip = true;
                        else openList.remove(cell);
                    }

                }
                if(skip) continue;
                for (Cell cell: closedList){
                    if(cell.getX() == child.getX() && cell.getY() == child.getY()){
                        if(cell.Fscore < child.Fscore) skip = true;
                    }
                }
                if(skip) continue;
                openList.add(child);

            }

            boolean added = false;
            for (Cell cell: closedList){
                if(cell.getX() == currentCell.getX() && cell.getY() == currentCell.getY()){
                    if(cell.Fscore > currentCell.Fscore){
                        closedList.remove(cell);
                        closedList.add(currentCell);
                    }
                    added = true;
                    break;
                }
            }
            if(!added) closedList.add(currentCell);


















        }






    }

    public ArrayList<Cell> directionsAvailable(int x, int y, Cell parent){
        ArrayList<Directions> directions = new ArrayList<>();
        if(!checkUp(x,y)) directions.add(Directions.N);
        if(!checkRight(x,y)) directions.add(Directions.E);
        if(!checkDown(x,y)) directions.add(Directions.S);
        if(!checkLeft(x,y)) directions.add(Directions.W);
        ArrayList<Cell> children = new ArrayList<>();

        for (Directions d: directions) {
            Cell newCell = null;
            if(d == Directions.N) newCell = new Cell(x,y - 1);
            if(d == Directions.E) newCell = new Cell(x + 1,y);
            if(d == Directions.S) newCell = new Cell(x,y + 1);
            if(d == Directions.W) newCell = new Cell(x - 1,y);
            newCell.parent = parent;
            children.add(newCell);

        }
        return children;

    }

    //check for node point
    //only 1 direction
    //more than 2 directions
    //so check there are not only 2 directions

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
