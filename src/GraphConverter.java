import java.sql.Struct;
import java.util.ArrayList;
//useless class. Original purpose was to convert the maze into a network of nodes at every turning point
public class GraphConverter
{
    Grid grid;

    Boolean[][] nodes;
    Boolean[][] prevSeen;
    int gridHeight;
    int gridWidth;
    Cell origin;

    enum Directions {NORTH,EAST,SOUTH,WEST}

    public GraphConverter(Grid grid){
        this.grid = new Grid(grid);

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

        convert();
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
