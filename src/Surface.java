import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Surface extends JPanel implements ActionListener {
    private Timer timer;
    final int gridWidth = 51;
    final int gridHeight = 51;
    private int pixelWidth;
    private int pixelHeight;

    ArrayList<Cell> cells;
    Grid grid;

    Boolean[][] nodes;
    Boolean[][] path;

    public MazeSolver mazeSolver;


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
        MazeGenerator mazegen = new MazeGenerator(this.grid);
        this.grid = mazegen.generatedGrid();

        drawGrid = true;

        GraphConverter graphConverter = new GraphConverter(grid);

        this.nodes = graphConverter.nodes;
        mazeSolver = new MazeSolver(grid);
        showPath();


    }

    public void showPath(){
        path = new Boolean[grid.getGridWidth()][grid.getGridHeight()];
        for (int i = 0; i < grid.getGridWidth(); i++) {
            for (int j = 0; j < grid.getGridHeight(); j++) {
                path[i][j] = false;
            }
        }

        Cell target = mazeSolver.target;
        Cell tempCell = target;
        while(!tempCell.equals(mazeSolver.origin)){
            tempCell = tempCell.parent;
            path[tempCell.getX()][tempCell.getY()] = true;
        }

    }

    private void initTimer() {
        timer = new Timer(17, this);
        timer.start();

    }

    private void doDrawing(Graphics2D g2d){
        pixelWidth = getWidth();
        pixelHeight = getHeight();
        grid.setPixels(pixelWidth,pixelHeight);
        g2d.setPaint(new Color(0, 0, 0));
        for (Cell cell:cells) {
            if(path[cell.getX()][cell.getY()] == true){
                g2d.setPaint(new Color(0, 255, 0));
                grid.drawCell(g2d,cell);
                g2d.setPaint(new Color(0, 0, 0, 244));

            }
            if(cell.getX() == gridWidth - 2 && cell.getY() == gridHeight - 2){
                g2d.setPaint(new Color(0, 255, 0));
                grid.drawCell(g2d,cell);
                g2d.setPaint(new Color(0, 0, 0, 244));
            }
            else if(!cell.isAlive()) continue;
            else grid.drawCell(g2d,cell);
        }
        g2d.setPaint(new Color(0, 0,0));
        if(drawGrid) Grid.drawGrid(g2d,gridWidth,gridHeight,pixelWidth,pixelHeight);
    };

    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        doDrawing(g2d);
        g2d.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
