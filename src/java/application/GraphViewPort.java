package src.java.application;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import src.java.functions.Graph;


public class GraphViewPort extends Canvas
{
    private Point[] x_axis_points;
    private Point[] y_axis_points;

    public GraphViewPort(Dimension dimension)
    {
        this.setBackground(new Color(12, 12, 12));
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setVisible(true);
        int height = (int)getSize().getHeight();
        int width = (int)getSize().getWidth();
        this.x_axis_points = new Point[]{new Point(0, (int)height/2), new Point((int)width, (int)height/2)};
        this.y_axis_points = new Point[]{new Point((int)width/2, 0), new Point((int)width/2, (int)height)};
        GraphRegister.get().set_scale(width, height);
        GraphRegister.get().register_graph(new Graph("x^x", 0));
        GraphRegister.get().register_graph(new Graph("x*(-1/3)-25", 1));
        GraphRegister.get().register_graph(new Graph("(-1)*(x-25)^2 + 14", 2));
        GraphRegister.get().calculate_points();

    }

    public void adjust_axis()
    {
        //TODO add ability to adjust center point
    }


    public void paint(Graphics g)
    {
        System.out.println("painting...");
        super.paint(g);
        g.setColor(new Color(255, 255, 255));
        g.drawLine(x_axis_points[0].x, x_axis_points[0].y, x_axis_points[1].x, x_axis_points[1].y);
        g.drawLine(y_axis_points[0].x, y_axis_points[0].y, y_axis_points[1].x, y_axis_points[1].y);
        paint_graphs(g);
    }


    public void paint_graphs(Graphics g)
    {
        for (Point[] arr_points : GraphRegister.get().get_values())
        {
            for(int i = 1; i< arr_points.length; i++)
            {
                g.drawLine(arr_points[i-1].x, arr_points[i-1].y, arr_points[i].x, arr_points[i].y);
            }
        }
    }
}
