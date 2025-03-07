package src.java.application;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JFrame;


public class Window extends JFrame
{
    private Container pane;

    public Window()
    {
        Dimension dimension = new Dimension(1280, 720);
        this.pane = getContentPane();

        setSize(dimension);
        setTitle("Graphical Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        pane.setLayout(new FlowLayout());
        pane.setBackground(new Color(20,22,25));
        Canvas canvas = new Canvas();

        
    }

    public void run()
    {
        EventQueue.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                Window w = new Window();
                w.setVisible(true);
            }
        });

    }
    
}
