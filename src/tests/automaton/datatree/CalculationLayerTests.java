package src.tests.automaton.datatree;

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
        assertTrue(expect.equals_node(c));

        type = CalculationType.MULTIPLICATION;
        change_values(12.0, 13.0, 3.0);
        c = Calculation.of(Calculation.of(a, b, type), Calculation.of(a, b, type), type);
        c = c.align_same_binding_strengths();
        expect = CalculationLayer.of(type).add(a).add(b).add(a).add(b);

        assertTrue(expect.equals_node(c));
        type = CalculationType.ADDITION;
        change_values(12.0, 13.0, 3.0);
        c = Calculation.of(Calculation.of(a, b, type), Calculation.of(Calculation.of(a, b, type), b, type), type);
        c = c.align_same_binding_strengths();
        expect = CalculationLayer.of(type).add(a).add(b).add(a).add(b).add(b);
        assertTrue(expect.equals_node(c));

        type = CalculationType.MULTIPLICATION;
        change_values(12.0, 13.0, 3.0);
        c = Calculation.of(Calculation.of(a, b, type), Calculation.of(Calculation.of(a, b, type), b, type), type);
        c = c.align_same_binding_strengths();
        expect = CalculationLayer.of(type).add(a).add(b).add(a).add(b).add(b);
        assertTrue(expect.equals_node(c));

        type = CalculationType.MULTIPLICATION;
        change_values(12.0, 13.0, 3.0);
        c = Calculation.of(Calculation.of(Calculation.of(a, b, CalculationType.ADDITION), b, type), Calculation.of(Calculation.of(a, b, type), b, type), type);
        c = c.align_same_binding_strengths();
        expect = CalculationLayer.of(type).add(Calculation.of(a, b, CalculationType.ADDITION)).add(b).add(a).add(b).add(b);
        assertTrue(expect.equals_node(c));

        type = CalculationType.ADDITION;
        change_values(12.0, 13.0, 3.0);
        c = Calculation.of(Calculation.of(Calculation.of(a, b, CalculationType.MULTIPLICATION), b, type), Calculation.of(Calculation.of(a, b, type), b, type), type);
        c = c.align_same_binding_strengths();
        expect = CalculationLayer.of(type).add(Calculation.of(a, b, CalculationType.MULTIPLICATION)).add(b).add(a).add(b).add(b);
        assertTrue(expect.equals_node(c));

        type = CalculationType.MULTIPLICATION;
        change_values(12.0, 13.0, 3.0);
        c = Calculation.of(CalculationLayer.of(type).add(Calculation.of(a, b, CalculationType.ADDITION)).add(b).add(a), Calculation.of(Calculation.of(a, b, type), b, type), type);
        c = c.align_same_binding_strengths();
        expect = CalculationLayer.of(type).add(Calculation.of(a, b, CalculationType.ADDITION)).add(b).add(a).add(a).add(b).add(b);
        assertTrue(expect.equals_node(c));

        type = CalculationType.ADDITION;
        change_values(12.0, 13.0, 3.0);
        c = Calculation.of(Calculation.of(Calculation.of(a, b, CalculationType.MULTIPLICATION), b, type), Calculation.of(Calculation.of(a, b, type), b, type), type);
        c = c.align_same_binding_strengths();
        expect = CalculationLayer.of(type).add(Calculation.of(a, b, CalculationType.MULTIPLICATION)).add(b).add(a).add(b).add(b);
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
        assertTrue(expect.equals_node(c));
    }

    //TODO this seems to not function entirely as expected.
    @Test
    public void cut_redundant_nodes_x()
    {
        CalculationType type = CalculationType.ADDITION;
        change_values(13.3304, 4575.4293, 13.0);
        c = CalculationLayer.of(type).add(Calculation.of(a, Value.X_VALUE, type)).add(b);
        c = c.cut_reduntant_calculations();
        c = c.align_same_binding_strengths();
        c = c.cut_reduntant_calculations();
        Node expect = CalculationLayer.of(type).add(Value.X_VALUE).add(Value.of(av+bv));
        assertTrue(c.get(xv) == expect.get(xv));
        assertTrue(c.get(252.033) == expect.get(252.033));
        assertTrue(c.get(252.033) != expect.get(252.0331));
        System.out.println(c);
        System.out.println(expect);
        assertTrue(c.equals_node(expect));
        assertTrue(expect.equals_node(c));
    }

    @Test
    public void inverse()
    {
        Node d = Calculation.of(Value.of(33.03), Value.of(-13.024), CalculationType.MULTIPLICATION);
        change_values(13.0, 12.0, 123.3);
        c = CalculationLayer.of(CalculationType.ADDITION).add(a).add(b).add(d);
        assertTrue(c.get(55) * c.inverse(CalculationType.MULTIPLICATION).get(12) == 1.0);
        c = CalculationLayer.of(CalculationType.MULTIPLICATION).add(a).add(b).add(d);
        assertTrue(c.get(55) * c.inverse(CalculationType.MULTIPLICATION).get(12) == 1.0);
        c = CalculationLayer.of(CalculationType.ADDITION).add(a).add(b).add(d);
        assertTrue(c.get(55) + c.inverse(CalculationType.ADDITION).get(12) == 0.0);
        c = CalculationLayer.of(CalculationType.MULTIPLICATION).add(a).add(b).add(d);
        assertTrue(c.get(55) + c.inverse(CalculationType.ADDITION).get(12) == 0.0);
    }


}