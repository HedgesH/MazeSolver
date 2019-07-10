import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main extends JFrame implements KeyListener {
    Surface surface;

    public Main() {
        initUI();
    }

    //instantiate the window
    private void initUI() {
        surface = new Surface();
        add(surface);
        addKeyListener(this);
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

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE ) surface.reset();
        surface.pause();  }

    @Override
    public void keyReleased(KeyEvent e) { }
    //TODO: clean up code
    //TODO: put all code on github

}