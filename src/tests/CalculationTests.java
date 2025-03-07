package src.tests;


import src.java.automaton.datatree.Calculation;
import src.java.automaton.datatree.Node;
import src.java.automaton.datatree.Value;
import src.java.automaton.datatree.CalculationType;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CalculationTests
{
    private double av, bv, xv;
    private Node a, b, c;


    private void change_values(double a, double b, double x)
    {
        this.av = a;
        this.bv = b;
        this.xv = x;

        this.a = Value.of(av);
        this.b = Value.of(bv);
    }

    @Test
    public void Calculation_tests()
    {

        change_values(1.0, 1.0, 9.0);

        c = Calculation.of(a, b, CalculationType.ADDITION);
        assertTrue(c.get(xv) == av + bv);
        c = Calculation.of(a, b, CalculationType.SUBTRACTION);
        assertTrue(c.get(xv) == av - bv);
        c = Calculation.of(a, b, CalculationType.MULTIPLICATION);
        assertTrue(c.get(xv) == av * bv);
        c = Calculation.of(a, b, CalculationType.DIVISION);
        assertTrue(c.get(xv) == av / bv);
        c = Calculation.of(a, b, CalculationType.POWER);
        assertTrue(c.get(xv) == Math.pow(av, bv));
    }

    @Test
    public void negative_a_value()
    {
        change_values(-13.0, 2.9, 9.0);

        c = Calculation.of(a, b, CalculationType.ADDITION);
        assertTrue(c.get(xv) == av + bv);
        c = Calculation.of(a, b, CalculationType.SUBTRACTION);
        assertTrue(c.get(xv) == av - bv);
        c = Calculation.of(a, b, CalculationType.MULTIPLICATION);
        assertTrue(c.get(xv) == av * bv);
        c = Calculation.of(a, b, CalculationType.DIVISION);
        assertTrue(c.get(xv) == av / bv);
        change_values(-13.0, 3.0, 9.0);
        c = Calculation.of(a, b, CalculationType.POWER);
        assertTrue(c.get(xv) == Math.pow(av, bv));
    }


    @Test
    public void negative_b_value()
    {
        change_values(3.2, -2.9, 9.0);

        c = Calculation.of(a, b, CalculationType.ADDITION);
        assertTrue(c.get(xv) == av + bv);
        c = Calculation.of(a, b, CalculationType.SUBTRACTION);
        assertTrue(c.get(xv) == av - bv);
        c = Calculation.of(a, b, CalculationType.MULTIPLICATION);
        assertTrue(c.get(xv) == av * bv);
        c = Calculation.of(a, b, CalculationType.DIVISION);
        assertTrue(c.get(xv) == av / bv);
        c = Calculation.of(a, b, CalculationType.POWER);
        assertTrue(c.get(xv) == Math.pow(av, bv));

    }


    @Test
    public void negative_values()
    {
        change_values(-1.9, -5.32, 9.0);

        c = Calculation.of(a, b, CalculationType.ADDITION);
        assertTrue(c.get(xv) == av + bv);
        c = Calculation.of(a, b, CalculationType.SUBTRACTION);
        assertTrue(c.get(xv) == av - bv);
        c = Calculation.of(a, b, CalculationType.MULTIPLICATION);
        assertTrue(c.get(xv) == av * bv);
        c = Calculation.of(a, b, CalculationType.DIVISION);
        assertTrue(c.get(xv) == av / bv);
        change_values(-1.9, -5.00, 9.0);
        c = Calculation.of(a, b, CalculationType.POWER);
        assertTrue(c.get(xv) == Math.pow(av, bv));
    }


    @Test
    public void X_a_value()
    {
        change_values(3.2, -2.9, 9.0);
        a = Value.X_VALUE;

        c = Calculation.of(a, b, CalculationType.ADDITION);
        assertTrue(c.get(xv) == xv + bv);
        c = Calculation.of(a, b, CalculationType.SUBTRACTION);
        assertTrue(c.get(xv) == xv - bv);
        c = Calculation.of(a, b, CalculationType.MULTIPLICATION);
        assertTrue(c.get(xv) == xv * bv);
        c = Calculation.of(a, b, CalculationType.DIVISION);
        assertTrue(c.get(xv) == xv / bv);
        c = Calculation.of(a, b, CalculationType.POWER);
        assertTrue(c.get(xv) == Math.pow(xv, bv));
    }


    @Test
    public void X_b_value()
    {
        change_values(3.2, -2.9, 9.0);
        b = Value.X_VALUE;

        c = Calculation.of(a, b, CalculationType.ADDITION);
        assertTrue(c.get(xv) == av + xv);
        c = Calculation.of(a, b, CalculationType.SUBTRACTION);
        assertTrue(c.get(xv) == av - xv);
        c = Calculation.of(a, b, CalculationType.MULTIPLICATION);
        assertTrue(c.get(xv) == av * xv);
        c = Calculation.of(a, b, CalculationType.DIVISION);
        assertTrue(c.get(xv) == av / xv);
        c = Calculation.of(a, b, CalculationType.POWER);
        assertTrue(c.get(xv) == Math.pow(av, xv));
    }


    @Test
    public void X_values()
    {
        change_values(3.2, -2.9, 9.0);
        a = Value.X_VALUE;
        b = Value.X_VALUE;

        c = Calculation.of(a, b, CalculationType.ADDITION);
        assertTrue(c.get(xv) == xv + xv);
        c = Calculation.of(a, b, CalculationType.SUBTRACTION);
        assertTrue(c.get(xv) == xv - xv);
        c = Calculation.of(a, b, CalculationType.MULTIPLICATION);
        assertTrue(c.get(xv) == xv * xv);
        c = Calculation.of(a, b, CalculationType.DIVISION);
        assertTrue(c.get(xv) == xv / xv);
        c = Calculation.of(a, b, CalculationType.POWER);
        assertTrue(c.get(xv) == Math.pow(xv, xv));
    }


    @Test
    public void contains_X()
    {
        for (CalculationType type : CalculationType.values())
        {

            change_values(12.0, 13.0, 3.0);

            c = Calculation.of(a, b, type);
            assertTrue(!c.contains_X());

            change_values(12.0, 13.0, 3.0);
            a = Value.X_VALUE;
            c = Calculation.of(a, b, type);
            assertTrue(c.contains_X());


            change_values(12.0, 13.0, 3.0);
            b = Value.X_VALUE;
            c = Calculation.of(a, b, type);
            assertTrue(c.contains_X());

            change_values(12.0, 13.0, 3.0);
            b = Value.X_VALUE;
            a = Value.X_VALUE;
            c = Calculation.of(a, b, type);
            assertTrue(c.contains_X());
        }
    }


    @Test
    public void contains_X_depth_II()
    {
        for (CalculationType type : CalculationType.values())
        {

            change_values(12.0, 13.0, 3.0);

            c = Calculation.of(Calculation.of(a, Value.of(0), type), Calculation.of(Value.of(0), b, type), type);
            assertTrue(!c.contains_X());

            change_values(12.0, 13.0, 3.0);
            a = Value.X_VALUE;
            c = Calculation.of(Calculation.of(a, Value.of(0), type), Calculation.of(Value.of(0), b, type), type);
            assertTrue(c.contains_X());


            change_values(12.0, 13.0, 3.0);
            b = Value.X_VALUE;
            c = Calculation.of(Calculation.of(a, Value.of(0), type), Calculation.of(Value.of(0), b, type), type);
            assertTrue(c.contains_X());

            change_values(12.0, 13.0, 3.0);
            b = Value.X_VALUE;
            a = Value.X_VALUE;
            c = Calculation.of(Calculation.of(a, Value.of(0), type), Calculation.of(Value.of(0), b, type), type);
            assertTrue(c.contains_X());
        }
    }


    @Test
    public void cut_redundant_nodes()
    {
        ///TODO this requires some sort of accessibility functionality

    }
}