import java.util.ArrayList;

public class Cell {
    private int x;
    private int y;
    private boolean alive;
    private int neighbours;
    public int Fscore;
    public int Gscore;
    public int Hscore;
    public Cell parent;
    public ArrayList<Cell> children;

    public int getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(int neighbours) {
        this.neighbours = neighbours;
    }

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        this.alive = false;
        Fscore = 0;
        Gscore = 0;
        Hscore = 0;
        children = new ArrayList<>();
        neighbours = 0;
    }

    public int getX() { return x; }

    public void setX(int x) { this.x = x; }

    public int getY() { return y; }

    public void setY(int y) { this.y = y;}

    public boolean isAlive() { return alive; }

    public void setAlive(boolean alive) { this.alive = alive; };




}
