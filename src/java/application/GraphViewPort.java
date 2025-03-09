package src.java.application;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.JScrollBar;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import src.java.global.Config;



public class GraphViewPort extends JPanel
{
    private Point[] x_axis_points;
    private Point[] y_axis_points;
    private JPanel view_port_settings;

    public GraphViewPort(Dimension dimension)
    {
        this.setBackground(Config.GRAPH_VIEWPORT_COLOR);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setVisible(true);
        int height = (int)getSize().getHeight();
        int width = (int)getSize().getWidth();
        this.x_axis_points = new Point[]{new Point(0, (int)height/2), new Point((int)width, (int)height/2)};
        this.y_axis_points = new Point[]{new Point((int)width/2, 0), new Point((int)width/2, (int)height)};
        GraphRegister.get().set_dimension(width, height);
        GraphRegister.get().calculate_points();

        view_port_settings = new JPanel();
        JScrollBar scale = new JScrollBar(JScrollBar.HORIZONTAL, 1,1, 1, 1000);
        scale.setPreferredSize(new Dimension(800, 20));
        scale.addAdjustmentListener(new AdjustmentListener()
        {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e)
            {
                GraphRegister.get().set_scale(e.getValue());
            }
        });
        view_port_settings.add(scale);

    }

    public void adjust_axis()
    {
        //TODO add ability to adjust center point
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        System.out.println("painting...");
        super.paintComponent(g);
        g.setColor(Config.GRAPH_VIEWPORT_DRAWING_COLOR);
        g.drawLine(x_axis_points[0].x, x_axis_points[0].y, x_axis_points[1].x, x_axis_points[1].y);
        g.drawLine(y_axis_points[0].x, y_axis_points[0].y, y_axis_points[1].x, y_axis_points[1].y);
        paint_graphs(g);
    }


    public void paint_graphs(Graphics g)
    {
        for (int[][] arr_values : GraphRegister.get().get_values())
        {
            g.drawPolyline(arr_values[0], arr_values[1], arr_values[1].length);
        }
    }


    public JPanel get_settings()
    {
        return view_port_settings;
    }
}
