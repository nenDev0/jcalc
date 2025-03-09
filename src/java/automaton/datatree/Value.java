package src.java.automaton.datatree;

public class Value implements Node
{

    public static final Value X_VALUE = new Value();

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



}
