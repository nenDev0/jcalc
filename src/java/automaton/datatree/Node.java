package src.java.automaton.datatree;
public abstract interface Node
{

    public double get(double X, boolean log);

    /**
     * Returns the value contained on this node.
     *
     * @param X - the x-parameter used in calculations
     * @return result as double
     */
    public double get(double X);

    /**
     * Cuts all calculations, which do not require the x-parameter.
     * These are supposed to be replaced by Value instances of
     * the deterministic precalculated result.
     *
     *
     */
    public void cut_reduntant_calculations();

    /**
     * In the case of a Calculation Instance, this will return true, if X is found anywhere within the tree.
     * In the case of a Value Instance, this will return true, if the value itself is X.
     *
     * @return boolean
     */
    public boolean contains_X();
}
