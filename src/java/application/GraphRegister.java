package src.java.application;

import src.java.functions.Point;
import java.util.TreeMap;

import src.java.functions.Graph;

public class GraphRegister
{


    private static final GraphRegister instance_self = new GraphRegister();

    private TreeMap<Graph, java.awt.Point[]> map_graphs_to_points = new TreeMap<Graph, java.awt.Point[]>();
    private int WIDTH;
    private int HEIGHT;

    private double width_x;
    private double height_y;

    private GraphRegister() {}


    public static GraphRegister get()
    {
        return instance_self;
    }


    public void register_graph(Graph graph)
    {
        System.out.println("added new graph:" + graph.get_ID());
        map_graphs_to_points.put(graph, calculate_points(graph));
    }


    public void set_scale(int width, int height)
    {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.width_x = width/10;
        this.height_y = height/10;
        calculate_points();
    }


    public void calculate_points()
    {
        for (Graph graph : map_graphs_to_points.keySet())
        {
            map_graphs_to_points.put(graph, calculate_points(graph));
        }
    }


    private java.awt.Point[] calculate_points(Graph graph)
    {
        System.out.println("GraphRegister is calculating...");
        graph.calculate_points((double)width_x, (double)height_y, 2000, Point.of(0,0));
        Point[] arr_pts = graph.get_points();
        java.awt.Point[] arr_awt_pts = new java.awt.Point[arr_pts.length];

        for (int i = 0; i < arr_pts.length; i++)
        {
            Point p = arr_pts[i];
            arr_awt_pts[i] = new java.awt.Point((int)(p.get_x()/width_x*WIDTH + WIDTH/2), (int)(-p.get_y()/height_y*HEIGHT + HEIGHT/2));
        }
        return arr_awt_pts;
    }


    public Iterable<java.awt.Point[]> get_values()
    {
        return map_graphs_to_points.values();
    }

}
