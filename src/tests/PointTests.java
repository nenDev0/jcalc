package src.tests;


import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import src.java.functions.Point;

public class PointTests {

    private double x;
    private double y;
    private Point p;

    private void change_values(double x, double y)
    {
        this.x = x;
        this.y = y;
    }


    @Test
    public void construction()
    {
        change_values(12.0, 6.0);
        p = Point.of(x, y);
        assertTrue(p.get_x() == x);
        assertTrue(p.get_y() == y);

        change_values(-12.0, 6.0);
        p = Point.of(x, y);
        assertTrue(p.get_x() == x);
        assertTrue(p.get_y() == y);

        change_values(12.0, -6.0);
        p = Point.of(x, y);
        assertTrue(p.get_x() == x);
        assertTrue(p.get_y() == y);

        change_values(-12.0, -6.0);
        p = Point.of(x, y);
        assertTrue(p.get_x() == x);
        assertTrue(p.get_y() == y);

        change_values(-0.0, -0.0);
        p = Point.of(x, y);
        assertTrue(p.get_x() == x);
        assertTrue(p.get_y() == y);

        change_values(-0.213, -0.00206);
        p = Point.of(x, y);
        assertTrue(p.get_x() == x);
        assertTrue(p.get_y() == y);

    }


    @Test
    public void construction_throws()
    {
        change_values(Double.NaN, y);
        assertThrows(IllegalArgumentException.class, () -> Point.of(x, y));

        change_values(x, Double.NaN);
        assertThrows(IllegalArgumentException.class, () -> Point.of(x, y));

        change_values(Double.NaN, Double.NaN);
        assertThrows(IllegalArgumentException.class, () -> Point.of(x, y));


        change_values(Double.POSITIVE_INFINITY, y);
        assertThrows(IllegalArgumentException.class, () -> Point.of(x, y));

        change_values(x, Double.POSITIVE_INFINITY);
        assertThrows(IllegalArgumentException.class, () -> Point.of(x, y));

        change_values(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        assertThrows(IllegalArgumentException.class, () -> Point.of(x, y));


        change_values(Double.NEGATIVE_INFINITY, y);
        assertThrows(IllegalArgumentException.class, () -> Point.of(x, y));

        change_values(x, Double.NEGATIVE_INFINITY);
        assertThrows(IllegalArgumentException.class, () -> Point.of(x, y));

        change_values(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        assertThrows(IllegalArgumentException.class, () -> Point.of(x, y));


        change_values(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        assertThrows(IllegalArgumentException.class, () -> Point.of(x, y));

        change_values(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
        assertThrows(IllegalArgumentException.class, () -> Point.of(x, y));


        change_values(Double.NaN, Double.POSITIVE_INFINITY);
        assertThrows(IllegalArgumentException.class, () -> Point.of(x, y));

        change_values(Double.NaN, Double.NEGATIVE_INFINITY);
        assertThrows(IllegalArgumentException.class, () -> Point.of(x, y));

        change_values(Double.POSITIVE_INFINITY, Double.NaN);
        assertThrows(IllegalArgumentException.class, () -> Point.of(x, y));

        change_values(Double.NEGATIVE_INFINITY, Double.NaN);
        assertThrows(IllegalArgumentException.class, () -> Point.of(x, y));
    }

    public void override_values()
    {
        change_values(12.0, 2.0);
        p = Point.of(x, y);
        assertTrue(p.get_x() == x);
        assertTrue(p.get_y() == y);
        change_values(2.0, -2.0);
        p.override(x, y);
        assertTrue(p.get_x() == x);
        assertTrue(p.get_y() == y);
    }
}
