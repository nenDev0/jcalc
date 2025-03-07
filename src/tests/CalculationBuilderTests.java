package src.tests;


import src.java.automaton.CalculationBuilder;
import src.java.automaton.datatree.Node;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CalculationBuilderTests
{
    private String func;
    private Node tree;
    private double value;

    @Test
    public void binding_strength_II()
    {
        func = "2+3";
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == 5.0);

        func = "2-3";
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == -1.0);
    }


    @Test
    public void binding_strength_I()
    {
        func = "2*3";
        System.out.println("begin testing.:" + func);
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == 6.0);

        func = "2/3";
        System.out.println("begin testing.:" + func);
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == 2.0/3.0);

        func = "2^3";
        System.out.println("begin testing.:" + func);
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == Math.pow(2.0,3.0));
    }


    @Test
    public void longer_numbers()
    {
        func = "203+313";
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == 516.0);

        func = "2-3";
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == -1.0);
    }


    @Test
    public void decimal_points()
    {
        func = "203.12+313.39";

        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == 516.51);

    }


    @Test
    public void mixed_binding_strength_I()
    {
        func = "2*3+6";
        System.out.println("begin testing.:" + func);
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == 12.0);

        func = "6+2*3";
        System.out.println("begin testing.:" + func);
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == 12.0);

    }


    @Test
    public void mixed_binding_strength_II()
    {
        func = "6+2*3+3";
        System.out.println("begin testing.:" + func);
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == 15.0);

        func = "6*2+3*3";
        System.out.println("begin testing.:" + func);
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == 21.0);

        func = "6*2/3-3+2";
        System.out.println("begin testing.:" + func);
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == 3.0);
    }


    @Test
    public void mixed_binding_strength_III()
    {
        func = "6^2/3-3+2";
        System.out.println("begin testing.:" + func);
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == 11.0);

        func = "-3+2+6^2/3";
        System.out.println("begin testing.:" + func);
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == 11.0);

        func = "-3+2+6/2^3";
        System.out.println("begin testing.:" + func);
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == -1.0/4.0);
    }


    @Test
    public void brackets()
    {
        func = "(2*3)";
        System.out.println("begin testing.:" + func);
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == 6.0);

        func = "(2*3)+(2*3)";
        System.out.println("begin testing.:" + func);
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == 12.0);

        func = "(2*3)+(2*3)";
        System.out.println("begin testing.:" + func);
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == 12.0);
    }



    @Test
    public void begin_negated()
    {
        func = "-2+3";
        System.out.println("begin testing.:" + func);
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == 1.0);

        func = "-2-3";
        System.out.println("begin testing.:" + func);
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == -5.0);

        func = "-2*3";
        System.out.println("begin testing.:" + func);
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == -6.0);

        func = "-2*3+3";
        System.out.println("begin testing.:" + func);
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == -3.0);

        func = "-2+3*3";
        System.out.println("begin testing.:" + func);
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == 7.0);

        func = "-2-3*3";
        System.out.println("begin testing.:" + func);
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == -11.0);

        func = "-2*3-3*3";
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == -15.0);

        func = "-2*3+3*3";
        tree = CalculationBuilder.build(func);
        value = tree.get(-20.8);
        System.out.println(value);
        assertTrue(value == 3.0);
    }


    @Test
    public void x()
    {
        func = "x";
        tree = CalculationBuilder.build(func);
        for(double x = -20.0; x < 20.0; x=x+0.5)
        {
            value = tree.get(x);
            System.out.println(value);
            assertTrue(value == x);
        }
    }


    @Test
    public void contains_x_values()
    {
        func = "x+12";
        tree = CalculationBuilder.build(func);
        value = tree.get(-3.0);
        System.out.println(value);
        assertTrue(value == 9.0);

        func = "12+x";
        tree = CalculationBuilder.build(func);
        value = tree.get(-3.0);
        System.out.println(value);
        assertTrue(value == 9.0);

        func = "3*x";
        tree = CalculationBuilder.build(func);
        value = tree.get(-3.0);
        System.out.println(value);
        assertTrue(value == -9.0);

        func = "3*x+6+x";
        tree = CalculationBuilder.build(func);
        value = tree.get(-3.0);
        System.out.println(value);
        assertTrue(value == -6.0);

        func = "x*x";
        tree = CalculationBuilder.build(func);
        value = tree.get(-3.0);
        System.out.println(value);
        assertTrue(value == Math.pow(-3.0, 2.0));

    }


    @Test
    public void more_functions()
    {
        func = "(-2*3)*2+3*3";
        tree = CalculationBuilder.build(func);
        value = tree.get(-3.0);
        System.out.println(value);
        assertTrue(value == -3.0);

        func = "(-2*3)^2+3*3";
        tree = CalculationBuilder.build(func);
        value = tree.get(-3.0);
        System.out.println(value);
        assertTrue(value == -45.0);

    }

}
