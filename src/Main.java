import javax.swing.*;
import java.awt.*;

public class Main extends JFrame  {
    Surface surface;

    public Main() {
        initUI();
    }

    private void initUI() {
        surface = new Surface();

        add(surface);

        setTitle("Maze");
        setSize(Toolkit.getDefaultToolkit().getScreenSize().width/2, Toolkit.getDefaultToolkit().getScreenSize().height/2);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main ex = new Main();
                ex.setVisible(true);
            }
        });
    }

    //TODO: show the route being carved?
    //TODO: clean up code
    //TODO: put all code on github

}