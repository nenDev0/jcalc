package src.java.functions;


public class Point implements Comparable<Point>
{

    protected double x;
    protected double y;


    private Point(double x, double y)
    {
        override(x, y);
    }


    public static Point of(double x, double y)
    {
        return new Point(x, y);
    }


    /**
     * Overrides the x,y values of this Point instance.
     *
     * @param x new x value
     * @param y new y value
     *
     * @throws IllegalArgumentException if x, y are NaN or Infinity
     *
     */
    public void override(double x, double y)
    {
        if (Double.isNaN(x) || Double.isNaN(y))
        {
            throw new IllegalArgumentException("Point cannot contain NaN Values!");
        }
        if (Double.isInfinite(x) || Double.isInfinite(y))
        {
            throw new IllegalArgumentException("Point cannot contain Infinity!");
        }
        this.x = x;
        this.y = y;
    }

    public double get_x()
    {
        return x;
    }

    public double get_y()
    {
        return y;
    }


    @Override
    public int compareTo(Point point)
    {
        if (x > point.get_x())
        {
            return 1;
        }
        if (x < point.get_x())
        {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Point{ "+x + ", "+y+" }";
    }
}
