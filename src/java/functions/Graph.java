package src.java.functions;

import java.util.TreeSet;

import src.java.automaton.CalculationBuilder;
import src.java.automaton.datatree.Node;

public class Graph implements Comparable<Graph>
{

    private Node top_node;
    private TreeSet<Point> set_points;
    private int ID;

    public Graph(String function, int ID)
    {
        this.ID = ID;
        top_node = CalculationBuilder.build(function);
        set_points = new TreeSet<Point>();
    }

    public int get_ID()
    {
        return ID;
    }


    public void set_function(String function)
    {
        top_node = CalculationBuilder.build(function);
    }


    public void calculate_points(double width, double height, int precision, Point center)
    {
        set_points.clear();
        double x_begin =  center.x - width/2;
        double x_end =  center.x + width/2;
        double y_min = center.y - height/2;
        double y_max = center.y + height/2;

        double diff_x = width/precision;
        /// used for margins
        boolean last_point_accepted = false;
        for (double x = x_begin; x < x_end; x+=diff_x)
        {
            double y = top_node.get(x);
            if (Double.isNaN(y))
            {
                last_point_accepted = false;
                continue;
            }
            Point point = Point.of(x, y);
            if (y_min < y && y < y_max)
            {
                /// if the last point was not within the interval,
                /// this ensures the edges aren't empty
                if (last_point_accepted == false)
                {
                    double y_val = top_node.get(x-diff_x);
                    if (!Double.isNaN(y_val))
                    {
                        Point p = Point.of(x-diff_x, y_val);
                        System.out.println("new point:" + p);
                        set_points.add(p);
                    }
                }
                last_point_accepted = true;
                System.out.println("new point:" + Point.of(x, y));
                set_points.add(point);
            }
            else
            {
                if (last_point_accepted == true)
                {
                    System.out.println("new point:" + point);
                    set_points.add(point);
                }
                last_point_accepted = false;
            }
        }
    }


    public Point[] get_points()
    {
        Point[] arr_points = new Point[set_points.size()];
        int i=0;
        for (Point point : set_points)
        {
            arr_points[i] = point;
            i++;
        }
        return arr_points;
    }


    @Override
    public int compareTo(Graph graph)
    {
        if (ID > graph.get_ID())
        {
            return 1;
        }
        if (ID < graph.get_ID())
        {
            return -1;
        }
        return 0;
    }

}