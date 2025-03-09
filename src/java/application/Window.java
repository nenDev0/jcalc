package src.java.application;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.util.Optional;

import javax.swing.JFrame;

import src.java.global.Config;


public class Window extends JFrame
{

    private Container pane;
    private GraphViewPort GVP;
    private static final Window self_instance = new Window();


    public static Optional<Window> get()
    {
        if (self_instance == null)
        {
            return Optional.empty();
        }
        return Optional.of(self_instance);
    }


    private Window()
    {
        Dimension dimension = new Dimension(1680, 720);
        this.pane = getContentPane();
        ///
        setSize(dimension);
        setTitle("Graphical Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ///
        ///
        pane.setBackground(Config.BACKGROUND_COLOR);
        pane.setLayout(new FlowLayout());
        ///
        GVP = new GraphViewPort(dimension);
        GVP.setSize(dimension);
        pane.add(GVP);
        ///
        FunctionList function_array = FunctionList.get();
        pane.add(function_array.get_panel());
        pane.add(GVP.get_settings());
    }


    /**
     * This was my attempt to update everything within the window,
     * however this doesn't seem to have worked
     * in the slightest...
     *
     */
    public void update()
    {
        ///TODO this seems to not be working so great...
        System.out.println("update window called...");
        GVP.repaint();
        repaint(0);
        revalidate();
    }


    public void run()
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                self_instance.setVisible(true);
            }
        });
    }


}
