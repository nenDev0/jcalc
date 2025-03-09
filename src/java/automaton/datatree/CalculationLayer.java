package src.java.automaton.datatree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import src.java.global.Config;

public class CalculationLayer implements Node
{

    private LinkedList<Node> ll_nodes;
    private final CalculationType type;
    //TODO add inverse parameter & function and remove subtraction, division, etc...


    private CalculationLayer(CalculationType type)
    {
        System.out.println("CALCULATION LAYER CREATED");
        ll_nodes = new LinkedList<Node>();
        if (type == null)
        {
            throw new NullPointerException("CalculationType cannot be null!");
        }
        this.type = type;
    }


    public CalculationLayer add(Node node)
    {
        if (node == null)
        {
            throw new NullPointerException("Nodes cannot be null!");
        }
        ll_nodes.add(node);
        return this;
    }


    public CalculationLayer addAll(List<Node> nodes)
    {
        ll_nodes.addAll(nodes);
        return this;
    }


    public static CalculationLayer of(CalculationType type)
    {
        return new CalculationLayer(type);
    }


    @Override
    public double get(double X)
    {
        return get(X, true);
    }


    @Override
    public double get(double X, boolean log)
    {
        return get(this.ll_nodes, this.type, X, log);
    }


    @SuppressWarnings("unused")
    private static double get(LinkedList<Node> ll_nodes, CalculationType type, double X, boolean log)
    {
        if (log && Config.LOG_CALCULATION_STATE)
        {
            System.out.print("requesting value from calculation...: ");
        }
        double value;
        switch (type)
        {
            case ADDITION ->
            {
                value = 0;
                for (Node node : ll_nodes)
                {
                    value += node.get(X, true);
                    System.out.print(node.get(X, false) + ", ");
                }
                break;
            }
            case SUBTRACTION ->
            {
                Iterator<Node> iterator = ll_nodes.iterator();
                value = iterator.next().get(X, true);
                while(iterator.hasNext())
                {
                    Node node = iterator.next();
                    value += -node.get(X, true);
                    System.out.print(node.get(X, false) + ", ");
                }
                break;
            }
            case MULTIPLICATION ->
            {
                Iterator<Node> iterator = ll_nodes.iterator();
                value = 1;
                while(iterator.hasNext())
                {
                    Node node = iterator.next();
                    value *= node.get(X, true);
                    System.out.print(node.get(X, false) + ", ");
                }
                break;
            }
            case DIVISION ->
            {
                Iterator<Node> iterator = ll_nodes.iterator();
                value = iterator.next().get(X);
                while(iterator.hasNext())
                {
                    Node node = iterator.next();
                    value /= node.get(X, true);
                    System.out.print(node.get(X, false) + ", ");
                }
                break;
            }
            case POWER ->
            {
                Iterator<Node> iterator = ll_nodes.iterator();
                value = Math.pow(iterator.next().get(X, true), iterator.next().get(X, true));
                if (iterator.hasNext())
                {
                    System.out.println("Power nodes shouldn't have more than 2 nodes...");
                }
                break;
            }
            default -> throw new AssertionError();
        }
        System.out.println("type = " + type);
        return value;
    }


    @Override
    public Node cut_reduntant_calculations()
    {
        LinkedList<Node> ll_nodes_without_x = new LinkedList<Node>();
        /// We check for instances of Calculations, because else we would be replacing each Value instance with
        /// a new Value instance, which just creates unnecessary work for the GC.
        Iterator<Node> iterator = ll_nodes.iterator();
        while(iterator.hasNext())
        {
            Node node = iterator.next();
            if (node instanceof CalculationLayer || node instanceof Calculation)
            {
                /// search for possible cuts recursively (DFS) down the tree
                node = node.cut_reduntant_calculations();
                if (!node.contains_X())
                {
                    /// node does not contains X -> replace node with Value of node.
                    /// X can be set to anything here
                    node = Value.of(node.get(0));
                    ll_nodes_without_x.add(node);
                    iterator.remove();
                }
            }
        }
        Node value = Value.of(get(ll_nodes_without_x, this.type, 0, false));
        if (ll_nodes.size() == 0)
        {
            return value;
        }
        /// if the nodes add up to the neutral value of the layer, the value is dismissed
        if (value.get(0) == Value.get_neutral(this.type).get(0))
        {
            return this;
        }
        return this.add(value);
    }


    @Override
    public boolean contains_X()
    {
        for (Node node : ll_nodes)
        {
            if (node.contains_X())
            {
                return true;
            }
        }
        return false;
    }


    @Override
    public Node align_same_binding_strengths()
    {
        LinkedList<Node> ll_nodes_new = new LinkedList<Node>();
        for (Node node : ll_nodes)
        {
            ll_nodes_new.add(node.align_same_binding_strengths());
        }
        ll_nodes.clear();
        ll_nodes.addAll(ll_nodes_new);
        Iterator<Node> iterator = ll_nodes.iterator();
        while(iterator.hasNext())
        {
            Node node = iterator.next();
            if (node instanceof CalculationLayer calc)
            {
                if (calc.type == type)
                {
                    int index = ll_nodes.indexOf(calc);
                    ll_nodes.remove(index);
                    ll_nodes.addAll(index, calc.ll_nodes);
                    iterator = ll_nodes.iterator();
                    for (int i = 0; i < index-1; i++)
                    {
                        iterator.next();
                    }
                }
            }
            else if (node instanceof Calculation calc)
            {
                if (calc.get_type() == type)
                {
                    int index = ll_nodes.indexOf(node);
                    ll_nodes.remove(index);
                    ll_nodes.add(index, calc.get_b());
                    ll_nodes.add(index, calc.get_a());
                    iterator = ll_nodes.iterator();
                    for (int i = 0; i < index-1; i++)
                    {
                        iterator.next();
                    }
                }
            }
        }
        return this;
    }


    @Override
    public boolean equals_node(Node node)
    {
        if (node instanceof CalculationLayer layer)
        {
            Iterator<Node> iterator_i = ll_nodes.iterator();
            Iterator<Node> iterator_ii = layer.ll_nodes.iterator();
            while(iterator_i.hasNext() && iterator_ii.hasNext())
            {
                if (!iterator_i.next().equals_node(iterator_ii.next()))
                {
                    return false;
                }
            }
            /// one has more nodes...
            if (iterator_i.hasNext() || iterator_ii.hasNext())
            {
                return false;
            }
            return true;
        }
        return false;
    }


    @Override
    public Node inverse(CalculationType type)
    {
        Node node;
        switch (type) {
            case ADDITION, SUBTRACTION ->
            {
                node = Calculation.of(Value.of(-1), this, CalculationType.MULTIPLICATION);
                break;
            }
            case MULTIPLICATION, DIVISION ->
            {
                node = Calculation.of(Value.of(1), this, CalculationType.DIVISION);
                break;
            }
            /// not entirely sure, if it is necessary?
            /// case Power too complicated for now
            default -> {
                throw new AssertionError();
            }
        }
        return node;
    }


    @Override
    public String toString()
    {
        String s = "";
        Iterator<Node> iterator = ll_nodes.iterator();
        while(iterator.hasNext())
        {
            Node node = iterator.next();
            s = s + node.toString();
            if (iterator.hasNext())
            {
                s = s + CalculationType.get_char(type);
            }
        }
        return s;
    }
}
