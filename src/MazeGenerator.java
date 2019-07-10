import java.util.ArrayList;
import java.util.Random;
import java.util.zip.DeflaterInputStream;

public class MazeGenerator {

    ArrayList<Cell> walls;
    Grid grid;
    int gridWidth;
    int gridHeight;
    Random random;

    enum Directions {NORTH,EAST,SOUTH,WEST}

    public MazeGenerator(Grid grid){
        this.gridHeight = grid.getGridHeight();
        this.gridWidth = grid.getGridWidth();
        this.grid = new Grid(grid);
        walls = new ArrayList<>();
        random = new Random(System.currentTimeMillis());
        generate(1,1);


    }

    public Grid generatedGrid(){return grid;}

    public void generate(int x, int y)
    {
        grid.getCell(x,y).setAlive(false);
        ArrayList<Directions> directions = directionsAvailable(x,y);
        if(directions.isEmpty()) return;
        while(!directions.isEmpty()){
            Directions newDirection = directions.get(random.nextInt(directions.size()));
            directions.remove(newDirection);
            recursiveStep(newDirection,x,y);
            directions = directionsAvailable(x,y);
        }

    }

    public ArrayList<Directions> directionsAvailable(int x, int y){
        ArrayList<Directions> directions = new ArrayList<>();
        if(checkUp(x,y)) directions.add(Directions.NORTH);
        if(checkRight(x,y)) directions.add(Directions.EAST);
        if(checkDown(x,y)) directions.add(Directions.SOUTH);
        if(checkLeft(x,y)) directions.add(Directions.WEST);
        return directions;

    }

    public void recursiveStep(Directions direction, int x , int y){

        switch (direction){
            case NORTH:{
                grid.getCell(x,y-1).setAlive(false);
                generate(x, y - 2);
                return;
            }
            case EAST:{
                grid.getCell(x + 1,y).setAlive(false);
                generate(x + 2, y);
                return;
            }
            case SOUTH:{
                grid.getCell(x,y + 1).setAlive(false);
                generate(x, y + 2);
                return;
            }
            case WEST:{
                grid.getCell(x - 1,y).setAlive(false);
                generate(x - 2, y);
                return;
            }

        }

    }

    public boolean checkUp(int x, int y){
        if(y == 1) return false;
        if(!grid.getCell(x, y - 2).isAlive()) return false;
        else return true;
    };
    public boolean checkDown(int x, int y){
        if(y == gridHeight - 2  || y == gridHeight - 1) return false;
        if(!grid.getCell(x, y + 2).isAlive()) return false;
        else return true;
    };
    public boolean checkLeft(int x, int y){
        if(x == 1) return false;
        if(!grid.getCell(x - 2 , y).isAlive()) return false;
        else return true;
    };
    public boolean checkRight(int x, int y){
        if(x == gridWidth - 2 || x == gridWidth - 1) return false;
        if(!grid.getCell(x + 2, y).isAlive()) return false;
        else return true;
    };












}
