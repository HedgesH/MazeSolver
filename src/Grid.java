import java.awt.*;
import java.util.ArrayList;

public class Grid {

    private Cell[][] grid;
    private int gridWidth;
    private int gridHeight;
    private int pixelWidth;
    private int pixelHeight;
    private ArrayList<Cell> cells;

    public static void drawGrid(Graphics2D g, int x, int y, int width, int height){

        int dx = width/x;
        int dy = height/y;
        for (int i = 0; i <dx * x + 1 ; i+=dx) {
            g.drawLine(i, 0, i, dy * y);
        }
        for (int i = 0; i <dy * y + 1; i+= dy) {
            g.drawLine(0, i, dx * x, i);
        }
    };

    public static void drawCell(Graphics2D g, int x, int y, int gridWidth, int gridHeight, int width, int height){

        int dx = (width/gridWidth) * x;
        int dy = (height/gridHeight) * y;


        Rectangle rect = new Rectangle(dx, dy, (width/gridWidth), (height/gridHeight));
        g.fill(rect);
    };

    public void drawCell(Graphics2D g, Cell cell){

        int dx = (pixelWidth/gridWidth) * cell.getX();
        int dy = (pixelHeight/gridHeight) * cell.getY();


        Rectangle rect = new Rectangle(dx, dy, (pixelWidth/gridWidth), (pixelHeight/gridHeight));
        g.fill(rect);
    };

    public void setPixels(int pixelWidth, int pixelHeight){
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;
    }
    
    public Grid(ArrayList<Cell> cell, int gridWidth, int gridHeight, int pixelWidth, int pixelHeight){
        grid = new Cell[gridWidth][gridHeight];
        int counter = 0;
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight ; j++) {

                grid[i][j] = cell.get(counter);
                counter++;

            }
        }
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;
        this.cells = cell;

    }

    public Grid(Grid grid){
        this.grid = grid.getGrid();
        this.gridWidth = grid.getGridWidth();
        this.gridHeight = grid.getGridHeight();
        this.cells = grid.getCells();
    }

    public Grid(Grid grid, ArrayList<Cell> cell, int gridHeight, int gridWidth){
        this.grid = new Cell[gridWidth][gridHeight];
        ArrayList<Cell> cells = new ArrayList<>();

//        //smaller
//        if(grid.getGridWidth() > gridWidth ){
//            for (int i = gridWidth; i <grid.getGridWidth() ; i++) {
//                for (int j = gridHeight; j <grid.getGridHeight() ; j++) {
//                    cells.remove(grid.getCell(i,j));
//                }
//            }
//            for (int i = 0; i <gridWidth ; i++) {
//                for (int j = 0; j <gridHeight ; j++) {
//                    this.grid[i][j] = grid.getCell(i,j);
//                }
//            }
//        }
//        //bigger
//        else{
//            for (int i = 0; i < gridWidth; i++) {
//                for (int j = 0; j < gridHeight; j++) {
//                    if(grid.getCell(i,j) != null) this.grid[i][j] = grid.getCell(i,j);
//                    else {
//                        this.grid[i][j] = new Cell(i,j);
//                        cells.add(this.grid[i][j]);
//                    }
//                }
//            }
//        }

        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                if(grid.getCell(i,j) != null) this.grid[i][j] = grid.getCell(i,j);
                else this.grid[i][j] = new Cell(i,j);
                cells.add(this.grid[i][j]);
            }
        }





        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.cells = cells;
    }

    public ArrayList<Cell> getCells(){ return cells;}


    public Cell[][] getGrid() { return grid; }

    public int getGridWidth() { return gridWidth;}

    public int getGridHeight() { return gridHeight;}

    public void setGridWidth(int w) { gridWidth = w;}

    public void setGridHeight(int h) { gridHeight = h;}



    public Cell getCell(int x, int y) {
        if(x >= gridWidth || y >= gridHeight) return null;
        return grid[x][y];
    }

    public boolean checkUp(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        if (y == 0) return false;
        try{return grid[x][y-1].isAlive();}
        catch (Exception e){
            throw new IllegalStateException(x + " " + y);
        }


    }

    public boolean checkDown(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        if (y == gridHeight - 1) return false;
        return grid[x][y+1].isAlive();
    }


    public boolean checkRight(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();
        if (x == gridWidth - 1) return false;
        return grid[x+1][y].isAlive();

    }

    
    public boolean checkLeft(Cell cell){
        int x = cell.getX();
        int y = cell.getY();
        if (x == 0) return false;
        return grid[x-1][y].isAlive();
    }

    
    public boolean checkUpRight(Cell cell) {
        if(cell.getY() == 0) return false;
        Cell cell2 = grid[cell.getX()][cell.getY()-1];
        return checkRight(cell2);
    }

    
    public boolean checkUpLeft(Cell cell) {
        if(cell.getY() == 0) return false;
        Cell cell2 = grid[cell.getX()][cell.getY()-1];
        return checkLeft(cell2);

    }

    
    public boolean checkDownRight(Cell cell) {
        if(cell.getY() == gridHeight - 1) return false;
        Cell cell2 = grid[cell.getX()][cell.getY()+1];
        return checkRight(cell2);

    }

    
    public boolean checkDownLeft(Cell cell) {
        if(cell.getY() == gridHeight - 1) return false;
        Cell cell2 = grid[cell.getX()][cell.getY()+1];
        return checkLeft(cell2);
    }



    
    public int neighbours(Cell cell) {
        int counter = 0;
        if(checkUp(cell)) counter++;
        if(checkDown(cell)) counter++;
        if(checkLeft(cell)) counter++;
        if(checkRight(cell)) counter++;
        if(checkUpRight(cell)) counter++;
        if(checkUpLeft(cell)) counter++;
        if(checkDownRight(cell)) counter++;
        if(checkDownLeft(cell)) counter++;
        return counter;
    }

    
    public void conditions(Cell cell, int neighbours) {
        if(neighbours == 3 && !cell.isAlive()){ cell.setAlive(true); return;}
        if(neighbours < 2){ cell.setAlive(false); return;}
        if(neighbours == 2 || neighbours == 3){ return;}
        else{ cell.setAlive(false); return;}
    }

}
