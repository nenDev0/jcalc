package src.java.application;

import src.java.functions.Point;

import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Optional;

import src.java.functions.Graph;

public class GraphRegister
{


    private static final GraphRegister instance_self = new GraphRegister();

    private TreeMap<Graph, int[][]> map_graphs_to_points = new TreeMap<Graph, int[][]>();
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
        map_graphs_to_points.put(graph, calculate_points(graph));
        System.out.println("added new graph:" + graph.get_ID());
    }


    public void register_graph(String function, int id)
    {
        Graph graph = new Graph(function, id);
        map_graphs_to_points.put(graph, calculate_points(graph));
        System.out.println("added new graph:" + graph.get_ID());
        update_window();
    }


    public void remove_graph(int id)
    {
        for (Iterator<Entry<Graph, int[][]>> entry_it = map_graphs_to_points.entrySet().iterator();
                entry_it.hasNext();)
        {
            Entry<Graph, int[][]> entry = entry_it.next();
            if (entry.getKey().get_ID() == id)
            {
                entry_it.remove();
                break;
            }
        }
    }

    public Optional<Graph> get_graph(int id)
    {
        for (Graph graph : map_graphs_to_points.keySet())
        {
            if (graph.get_ID() == id)
            {
                return Optional.of(graph);
            }
        }
        return Optional.empty();
    }


    public void set_dimension(int width, int height)
    {
        this.WIDTH = width;
        this.HEIGHT = height;
        set_scale(1);
        calculate_points();
    }

    public void set_scale(int scale)
    {
        this.width_x = WIDTH * scale / 100;
        this.height_y = HEIGHT * scale / 100;
        calculate_points();
    }

    public void calculate_points()
    {
        for (Graph graph : map_graphs_to_points.keySet())
        {
            map_graphs_to_points.put(graph, calculate_points(graph));
        }
        update_window();
    }

    public void calculate_points(int id)
    {
        for(Entry<Graph, int[][]> entry : map_graphs_to_points.entrySet())
        {
            if (entry.getKey().get_ID() == id)
            {
                entry.setValue(calculate_points(entry.getKey()));
                break;
            }
        }
        update_window();
    }

    private int[][] calculate_points(Graph graph)
    {
        System.out.println("GraphRegister is calculating...");
        graph.calculate_points((double)width_x, (double)height_y, 1000, Point.of(0,0));
        Point[] arr_pts = graph.get_points();
        int[][] arr_values = new int[2][arr_pts.length];

        for (int i = 0; i < arr_pts.length; i++)
        {
            Point p = arr_pts[i];
            arr_values[0][i] = (int)(p.get_x()/width_x*WIDTH + WIDTH/2);
            arr_values[1][i] = (int)(-p.get_y()/height_y*HEIGHT + HEIGHT/2);
        }
        return arr_values;
    }


    public Iterable<int[][]> get_values()
    {
        return map_graphs_to_points.values();
    }


    public void update_window()
    {
        ///TODO updating the window seems to require a scalar update...
        /// Window should update ass soon as the calculation is finished...
        Window.get().ifPresent(window -> window.update());
    }
}
