package src.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import src.java.automaton.datatree.CalculationType;
import src.java.automaton.datatree.Value;

public class ValueTests {

    @Test
    public void XVALUE_tests()
    {
        assertTrue(Value.X_VALUE.contains_X());
        assertTrue(Value.X_VALUE.get(0) == 0);
        assertTrue(Value.X_VALUE.get(-3) == -3);
        assertTrue(Value.X_VALUE.get(200) == 200);

    }



    @Test
    public void Value_tests()
    {
        Value v = Value.of(0);
        assertTrue(v.get(3) == 0);
        assertTrue(v.get(-3) == 0);
        assertTrue(v.get(200) == 0);
        v = Value.of(200);
        assertTrue(v.get(3) == 200);
        assertTrue(v.get(-3) == 200);
        assertTrue(v.get(200) == 200);
    }


    @Test
    public void inverse()
    {
        double vv = 12.3;
        Value v = Value.of(vv);
        assertTrue(v.inverse(CalculationType.ADDITION).get(0) == -vv);
        assertTrue(v.inverse(CalculationType.SUBTRACTION).get(0) == -vv);
        assertTrue(v.inverse(CalculationType.MULTIPLICATION).get(0) == 1/vv);
        assertTrue(v.inverse(CalculationType.DIVISION).get(0) == 1/vv);
    }


    @Test
    public void inverse_x()
    {
        double xv = 46.04512;
        Value x = Value.X_VALUE;
        assertTrue(x.inverse(CalculationType.ADDITION).get(xv) == -xv);
        assertTrue(x.inverse(CalculationType.SUBTRACTION).get(xv) == -xv);
        assertTrue(x.inverse(CalculationType.MULTIPLICATION).get(xv) == 1/xv);
        assertTrue(x.inverse(CalculationType.DIVISION).get(xv) == 1/xv);
    }
}
