package src.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import src.java.automaton.datatree.Calculation;
import src.java.automaton.datatree.CalculationLayer;
import src.java.automaton.datatree.CalculationType;
import src.java.automaton.datatree.Node;
import src.java.automaton.datatree.Value;


public class CalculationLayerTests
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
    public void align_same_binding_strengths()
    {
        CalculationType type = CalculationType.ADDITION;
        change_values(12.0, 13.0, 3.0);
        c = Calculation.of(Calculation.of(a, b, type), Calculation.of(a, b, type), type);
        c = c.align_same_binding_strengths();
        CalculationLayer expect = CalculationLayer.of(type).add(a).add(b).add(a).add(b);
        System.out.println(expect.toString());
        System.out.println(c.toString());
        assertTrue(expect.equals_node(c));
    }

    @Test
    public void cut_reduntant_calculations()
    {
        CalculationType type = CalculationType.ADDITION;
        change_values(12.0, 13.0, 3.0);
        c = Calculation.of(Calculation.of(a, b, type), Calculation.of(a, b, type), type);
        c = c.cut_reduntant_calculations();
        c = c.align_same_binding_strengths();
        Value expect = Value.of(12+13+12+13);
        System.out.println(expect.toString());
        System.out.println(c.toString());
        assertTrue(expect.equals_node(c));

    }
}