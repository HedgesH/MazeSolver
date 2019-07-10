import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class Surface extends JPanel implements ActionListener {
    private Timer timer;
    private int counter;
    final int gridWidth = 101;
    final int gridHeight = 101;
    private int pixelWidth;
    private int pixelHeight;
    private ArrayList<Cell> pathArray;

    ArrayList<Cell> cells;
    Grid grid;

    Boolean[][] nodes;
    Boolean[][] path;
    Boolean pause;

    private MazeSolver mazeSolver;
    private MazeGenerator mazeGen;

    private boolean drawGrid;

    public Surface(){
        super();
        initTimer();
        cells = new ArrayList<>();
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight ; j++) {
                Cell cell = new Cell(i,j);
                this.cells.add(cell);
                cell.setAlive(true);
            }
        }
        this.pixelHeight = getHeight();
        this.pixelWidth = getWidth();
        this.grid = new Grid(cells,gridWidth,gridHeight,pixelWidth,pixelHeight);
        mazeGen = new MazeGenerator(this.grid);
        this.grid = mazeGen.generatedGrid();
        drawGrid = true;
        mazeSolver = new MazeSolver(grid);
        showPath();

        pause = false;
    }

    //find the cells in the maze's solution
    public void showPath(){
        counter = 0;
        path = new Boolean[grid.getGridWidth()][grid.getGridHeight()];
        for (int i = 0; i < grid.getGridWidth(); i++) {
            for (int j = 0; j < grid.getGridHeight(); j++) {
                path[i][j] = false;
            }
        }

        pathArray = new ArrayList<>();

        Cell target = mazeSolver.target;
        Cell tempCell = target;
        while(!tempCell.equals(mazeSolver.origin)){
            pathArray.add(tempCell);
            tempCell = tempCell.parent;
            path[tempCell.getX()][tempCell.getY()] = true;
        }
        Collections.reverse(pathArray);
    }

    //slowly display path
    public void animatePath(int counter, Graphics2D g2d){

        int limit = min(counter,pathArray.size());

        for (int i = 0; i < limit ; i++) {
            Cell temp = pathArray.get(i);
            g2d.setPaint(new Color(0, 255, 0));
            grid.drawCell(g2d,temp);
            g2d.setPaint(new Color(0, 0, 0, 244));
        }

    }

    //a timer
    private void initTimer() {
        timer = new Timer(17, this);
        timer.start();

    }

    //this is what will be rendered onto the screen
    private void doDrawing(Graphics2D g2d){
        pixelWidth = getWidth();
        pixelHeight = getHeight();
        grid.setPixels(pixelWidth,pixelHeight);
        g2d.setPaint(new Color(0, 0, 0));
        for (Cell cell:cells) {
            if(cell.getX() == gridWidth - 2 && cell.getY() == gridHeight - 2){
                g2d.setPaint(new Color(255, 0, 16));
                grid.drawCell(g2d,cell);
                g2d.setPaint(new Color(0, 0, 0, 244));
            }
            else if(!cell.isAlive()) continue;
            else grid.drawCell(g2d,cell);
        }
        animatePath(counter,g2d);
        counter++;
        g2d.setPaint(new Color(0, 0,0));
        if(drawGrid) Grid.drawGrid(g2d,gridWidth,gridHeight,pixelWidth,pixelHeight);
    }

    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        doDrawing(g2d);
        g2d.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!pause) repaint();
    }

    public void pause(){ pause = !pause;}

    public void reset(){
        cells = new ArrayList<>();
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight ; j++) {
                Cell cell = new Cell(i,j);
                this.cells.add(cell);
                cell.setAlive(true);
            }
        }
        this.grid = new Grid(cells,gridWidth,gridHeight,pixelWidth,pixelHeight);
        mazeGen = new MazeGenerator(this.grid);
        this.grid = mazeGen.generatedGrid();
        drawGrid = true;
        mazeSolver = new MazeSolver(grid);
        showPath();

    }

}
