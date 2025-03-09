package src.java.automaton;

import src.java.automaton.datatree.Node;

/**
 * Wrapper class for the calculations.
 *
 */
public class Function
{
    Node top_node;

    private Function(String function)
    {
        top_node = CalculationBuilder.build(function);
        System.out.println("new function created: "+top_node.toString());
    }

    public static Function of(String function)
    {
        return new Function(function);
    }

    public double get(double X)
    {
        return top_node.get(X);
    }

    @Override
    public String toString()
    {
        return top_node.toString();
    }

    public boolean equals_func(Function func)
    {
        if (func.top_node.equals_node(top_node))
        {
            return true;
        }
        return false;
    }

    /// TODO equivalence tests
    /// TODO Function Types (Constants, linear functions, Polynomials, etc.)
    /// useful to reduce calculations (constants and linear functions only require 2 points)
}
