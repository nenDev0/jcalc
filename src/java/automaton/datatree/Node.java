package src.java.automaton.datatree;


public abstract interface Node
{


    /**
     *
     *
     * @param X
     * @param log
     *
     * @return
     */
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
    public Node cut_reduntant_calculations();


    /**
     * Pulls nodes, which have the same binding strength
     * up into a single Calculation node.
     *
     * This way a node structure, like:
     * a+(b+(c+d))
     * would result in:
     * a+b+c+d
     *
     */
    public Node align_same_binding_strengths();


    /**
     * In the case of a Calculation Instance, this will return true, if X is found anywhere within the tree.
     * In the case of a Value Instance, this will return true, if the value itself is X.
     *
     * @return boolean
     */
    public boolean contains_X();


    /**
     * Returns the inverse of the given value.
     *
     * @param X
     * @param type
     *
     * @return
     */
    public Node inverse(CalculationType type);


    /**
     *
     *
     * @param node
     *
     * @return
     */
    public boolean equals_node(Node node);


}
