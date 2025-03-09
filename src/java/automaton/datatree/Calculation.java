package src.java.automaton.datatree;

import src.java.global.Config;

public class Calculation implements Node
{

    private Node a;
    private Node b;
    private final CalculationType type;


    private Calculation(Node a, Node b, CalculationType type)
    {
        if (a == null || b == null)
        {
            throw new NullPointerException("Nodes cannot be null!");
        }
        if (type == null)
        {
            throw new NullPointerException("CalculationType cannot be null!");
        }
        this.a = a;
        this.b = b;
        this.type = type;
    }


    public static Calculation of(Node a, Node b, CalculationType type)
    {
        return new Calculation(a, b, type);
    }


    @Override
    public double get(double X)
    {
        return get(X, true);
    }


    @SuppressWarnings("unused")
    @Override
    public double get(double X, boolean log)
    {
        if (log && Config.LOG_CALCULATION_STATE)
        {
            System.out.println("requesting value from calculation...: " + a.get(X, false) +", " + b.get(X, false) + ", "+type);
        }
        double value;
        switch (this.type)
        {
            case ADDITION ->
            {
                value = a.get(X, true) + b.get(X, true);
                break;
            }
            case SUBTRACTION ->
            {
                value = a.get(X, true) - b.get(X, true);
                break;
            }
            case MULTIPLICATION ->
            {
                value = a.get(X, true) * b.get(X, true);
                break;
            }
            case DIVISION ->
            {
                value = a.get(X, true) / b.get(X, true);
                break;
            }
            case POWER ->
            {
                value = Math.pow(a.get(X, true), b.get(X, true));
                break;
            }
            default -> throw new AssertionError();
        }
        return value;
    }


    @Override
    public Node cut_reduntant_calculations()
    {
        if (!a.contains_X() && !b.contains_X())
        {
            return Value.of(get(0));
        }
        /// We check for instances of Calculations, because else we would be replacing each Value instance with
        /// a new Value instance, which just creates unnecessary work for the GC.
        if (a instanceof Calculation)
        {
            /// a contains X -> search for possible cuts recursively (DFS) down the tree
            a.cut_reduntant_calculations();
            if (!a.contains_X())
            {
                /// a does not contains X -> replace a with Value of a.
                /// X can be set to anything here
                a = Value.of(a.get(0));
            }
        }
        /// We check for instances of Calculations, because else we would be replacing each Value instance with
        /// a new Value instance, which just creates unnecessary work for the GC.
        if (b instanceof Calculation)
        {
            /// b contains X -> search for possible cuts recursively (DFS) down the tree
            b.cut_reduntant_calculations();
            if (!b.contains_X())
            {
                /// b does not contains X -> replace b with Value of b.
                /// X can be set to anything here
                b = Value.of(b.get(0));
            }
        }
        return this;
    }


    @Override
    public boolean contains_X()
    {
        if (a.contains_X() || b.contains_X())
        {
            return true;
        }
        return false;
    }


    @Override
    public Node align_same_binding_strengths()
    {
        if (a instanceof Calculation calc)
        {
            if (calc.type == type)
            {
                return CalculationLayer.of(type)
                        .add(calc.a)
                        .add(calc.b)
                        .add(b)
                    .align_same_binding_strengths();
            }
        }
        if (b instanceof Calculation calc)
        {
            if (calc.type == type)
            {
                return CalculationLayer.of(type)
                        .add(a)
                        .add(calc.a)
                        .add(calc.b)
                    .align_same_binding_strengths();
            }
        }

        a = a.align_same_binding_strengths();
        b = b.align_same_binding_strengths();
        return this;
    }


    public Node get_a()
    {
        return a;
    }


    public Node get_b()
    {
        return b;
    }


    public CalculationType get_type()
    {
        return type;
    }


    @Override
    public String toString()
    {
        return a.toString()+CalculationType.get_char(type)+b.toString();
    }

    public boolean equals_node(Node node)
    {
        if (node instanceof Calculation calc)
        {
            if (a.equals(calc.a) && b.equals(calc.b))
            {
                return true;
            }
            return false;
        }
        return false;
    }


}
