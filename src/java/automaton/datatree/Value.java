package src.java.automaton.datatree;

public class Value implements Node
{

    public static final Value X_VALUE = new Value();
    public static final Value ADDITION_NEUTRAL = new Value(0.0);
    public static final Value MULTIPLICATION_NEUTRAL = new Value(1.0);

    private final double value;


    /**
     * Used to initialize a Value instance, which contains a known double value.
     * @param X
     */
    private Value(double v)
    {
        this.value = v;
    }


    /**
     * Used to initialize a Value instance, which doesn't contain a known value.
     * The get() function will return the X value given.
     */
    private Value()
    {
        this.value = Double.NaN;
    }


    public static Value of(double v)
    {
        return new Value(v);
    }


    public static Value of(int v)
    {
        return new Value((double)v);
    }


    @Override
    public double get(double X, boolean log)
    {
        return get(X);
    }


    public double get(double X)
    {
        if (contains_X())
        {
            return X;
        }
        return value;
    }


    @Override
    public boolean contains_X()
    {
        /// Has to compare adresses. Since equals() may be shadowed at some point,
        /// this ensures that won't become a problem.
        /// value.equals(X_VALUE) => loop => overflow
        return super.equals(X_VALUE);
    }


    @Override
    public Node cut_reduntant_calculations()
    {
        return this;
    }


    @Override
    public Node align_same_binding_strengths()
    {
        return this;
    }

    @Override
    public String toString()
    {
        if (contains_X())
        {
            return "x";
        }
        return Double.toString(value);
    }

    @Override
    public boolean equals_node(Node node)
    {
        if (node instanceof Value val)
        {
            if (this.contains_X() && val.contains_X())
            {
                return true;
            }
            else if (this.contains_X() || val.contains_X())
            {
                return false;
            }
            if (val.value == this.value)
            {
                return true;
            }
        }
        return false;
    }


    @Override
    public Node inverse(CalculationType type)
    {
        Node node;
        switch (type)
        {
            case ADDITION, SUBTRACTION ->
            {
                if (contains_X())
                {
                    node = Calculation.of(Value.of(-1), this, CalculationType.MULTIPLICATION);
                }
                else
                {
                    node = Value.of(-value);
                }
                break;
            }
            case MULTIPLICATION, DIVISION ->
            {
                if (contains_X())
                {
                    node = Calculation.of(Value.of(1), this, CalculationType.DIVISION);
                }
                else
                {
                    node = Value.of(1/value);
                }
                break;
            }
            // not entirely sure, how power should work here
            default -> throw new AssertionError();
        }
        return node;
    }


    public static Node get_neutral(CalculationType type)
    {
        switch (type)
        {
            case ADDITION, SUBTRACTION ->
            {
                return ADDITION_NEUTRAL;
            }
            case MULTIPLICATION, DIVISION ->
            {
                return MULTIPLICATION_NEUTRAL;
            }
            default -> throw new AssertionError();
        }
    }

}
