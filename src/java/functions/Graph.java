package src.java.functions;

import java.util.TreeSet;

import src.java.automaton.CalculationBuilder;
import src.java.automaton.datatree.Node;

public class Graph
{

    private String function;
    private Node top_node;
    private TreeSet<Point> set_points;

    public Graph(String function)
    {
        this.function = function;
        top_node = CalculationBuilder.build(function);
        set_points = new TreeSet<Point>();
    }

    public void calculate_points(double width, double height, int precision, Point center)
    {
        double x_begin =  center.x + width/2;
        double x_end =  center.x - width/2;
        double y_min = center.y - height/2;
        double y_max = center.y + height/2;
        ///
        /// TODO: there needs to be some sort of overlap.
        /// Currently around the edges the graph would simply stop, very noticable at lower precision.
        double diff_x = width/precision;
        for (double x = x_begin; x < x_end; x+=diff_x)
        {
            double y = top_node.get(x);
            if (y_min < y && y < y_max)
            {
                set_points.add(Point.of(x, y));
            }
        }
    }

}