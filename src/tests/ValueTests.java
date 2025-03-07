package src.tests;

import static org.junit.Assert.*;

import org.junit.Test;

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
}
