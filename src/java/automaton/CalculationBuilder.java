package src.java.automaton;

import java.util.TreeMap;
import java.util.Map.Entry;

import src.java.automaton.datatree.Calculation;
import src.java.automaton.datatree.Node;
import src.java.automaton.datatree.Value;
import src.java.automaton.datatree.CalculationType;

import java.util.Optional;

public final class CalculationBuilder
{


    /// Dictionaries used within functions
    private static final char[] arr_numbers = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final char[] arr_multiplication_operators = new char[] {'*', '/'};
    private static final char[] arr_addition_operators = new char[] {'+', '-'};
    private static final char[] arr_power_operators = new char[] {'^', '\\'};

    private static final char[] arr_misc = new char[] {'(', ')'};
    private static final char[] arr_number_decimal_symbols = new char[] {',', '.'};


    private CalculationBuilder() {
    }


    /**
     * Returns the top-node of a tree, in which the given function is divided up into steps to allow for
     * easy calculation.
     *
     * Use Node.get(X) - to calculate the result
     *
     * @param func
     *
     * @return
     *  - Node -> Node is a tree, in which the whole interval is sorted recursively to create an
     *            accurate result, simulating proper mathematics.
     */
    public static Node build(String func)
    {
        func = func.replaceAll("\\s","");
        final char[] arr_char = func.toCharArray();
        Node node = build(arr_char, 0,arr_char.length);
        node.cut_reduntant_calculations();
        return node;
    }
    /**
     * Method for internal recursive usage of the build function.
     * Allows setting intervals to which the function obliges.
     *
     * @param chars
     * @param begin
     * @param end
     * @return
     *  - Node -> Node is a tree, in which the whole interval is sorted recursively to create an
     *            accurate result, simulating proper mathematics.
     */
    private static Node build(final char[] chars, int begin, int end)
    {
        Optional<Node> node;
        for (int j = CalculationType.binding_strength_count() - 1; j >= 0; j--)
        {
            node = divide_by_operators(chars, begin, end, CalculationType.binding_strength(j), j);
            if (node.isPresent())
            {
                return node.get();
            }
        }
        if (chars[begin] == 'x')
        {
            System.out.println("encountered x at: " +begin);
            return Value.X_VALUE;
        }
        return Value.of(decode_number(chars, begin, end));
    }


    /**
     * Divides the given interval into separate Nodes & build-method calls recursively.
     *
     * @param chars
     * @param begin
     * @param end
     * @param arr_types
     * @param binding_strength
     * @return
     *  - Optional.of(Node) -> Node is a tree, in which the whole interval is sorted recursively to
     *                         generate a mathematically accurate result.
     *                         The nodes on this depth should relate to the binding_strength parameter set
     *
     *  - Optional.empty()  -> The given interval could not be divided based on the binding_strength parameter set
     */
    private static Optional<Node> divide_by_operators(final char[] chars, int begin, int end, CalculationType[] arr_types, int binding_strength)
    {
        System.out.println("building function:" + chars + " [begin: " + begin + ", end: " + end + "]");
        Node a, b;
        CalculationType type;
        ///
        /// Reads through every character in the interval set by begin & end parameters.
        for (int i = begin; /*i < chars.length &&*/ i < end; i++)
        {
            /// Separate bracket into a new Calculation and call the build method accordingly.
            if (chars[i] == '(')
            {
                System.out.println("brackets were found...");
                int closing_bracket = find_end_bracket(chars, i + 1, end);
                if (begin < i && closing_bracket < end - 1)
                {
                    ///compare binding-strength of the operators on both sides
                    ///TODO read_operator may fail, if brackets are found instead. Example: ((a+b))
                    /// Does this apply?
                    CalculationType type_left = read_operator(chars[i-1]);
                    CalculationType type_right = read_operator(chars[closing_bracket+1]);
                    a = build(chars, begin, i - 1);
                    b = build(chars, i + 1, closing_bracket);
                    Node c = build(chars, closing_bracket+2, end);
                    if (CalculationType.binding_strength(type_left) < CalculationType.binding_strength(type_right))
                    {
                        return Optional.of(Calculation.of(Calculation.of(a, b, type_left), c, type_right));
                    }
                    else
                    {
                        return Optional.of(Calculation.of(a, Calculation.of(b, c, type_right), type_left));
                    }
                }
                else if (begin < i)
                {
                    a = build(chars, begin, i - 1);
                    b = build(chars, i + 1, end);
                    int j = 1;
                    type = read_operator(chars[i - j]);
                    return Optional.of(Calculation.of(a, b, read_operator(chars[i - 1])));
                }
                else if (closing_bracket < end - 1)
                {
                    a = build(chars, i + 1, closing_bracket);
                    b = build(chars, closing_bracket + 2, end);
                    type = read_operator(chars[closing_bracket + 1]);
                    return Optional.of(Calculation.of(a, b, type));
                }
                else
                {
                    return Optional.of(build(chars, i + 1, closing_bracket));
                }
            }
            else
            {
                for (CalculationType it_type : arr_types)
                {
                    if (chars[i] == CalculationType.get_char(it_type))
                    {
                        if (binding_strength == 2)
                        {
                            /// ensure +/- at the beginning of values aren't cut off
                            /// example (-2) would otherwise be cut into "" and "2"
                            if(i == begin)
                            {
                                continue;
                            }
                            ///TODO SUBTRACTION-METHOD this is an ugly solution. Find a better one?
                            ///
                            /// We ensure here, that when a negative value is added with a positive value
                            /// ..-3+2.. that they don't add up, but a subtracts from b.
                            /// We currently do this by testing, whether the number a has a sign in front of it
                            /// and for b we ensure that, if we sign it, it has to start with a number.
                            /// Otherwise we do b like normal.
                            try
                            {
                                if (read_operator(chars[begin-1]) == CalculationType.SUBTRACTION)
                                {
                                    a = build(chars, begin-1, i);
                                }
                                else
                                {
                                    a = build(chars, begin, i);
                                }
                            }
                            catch (Exception e)
                            {
                                a = build(chars, begin, i);
                            }
                            if (-1 == char_is_number(chars[i+1]))
                            {
                                b = build(chars, i + 1, end);
                            }
                            else
                            {
                                b = build(chars, i, end);
                            }
                            return Optional.of(Calculation.of(a, b, CalculationType.ADDITION));
                        }
                        a = build(chars, begin, i);
                        b = build(chars, i + 1, end);
                        return Optional.of(Calculation.of(a, b, it_type));
                    }
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Returns the index, at which this bracket closes.
     *
     * @param chars
     * @param begin
     * @param end
     * @return
     */
    private static int find_end_bracket(final char[] chars, int begin, int end)
    {
        int bracket_index = 1;
        for (int i = begin; i < chars.length && i < end; i++ )
        {
            switch (chars[i])
            {
                case '(' -> {bracket_index++; break;}
                case ')' -> {bracket_index--; break;}
                default  -> {break;}
            }
            if (bracket_index == 0)
            {
                System.out.println("closing bracket was found...");
                return i;
            }
        }
        throw new AssertionError("Bracket never closed!");
    }


    /**
     * Returns the index, at which this number value ends
     * Additionally decodes the number value and write it to c_out parameter.
     *
     * @param chars - char-array to read from
     * @param begin - beginning of interval in char array
     * @param end - end of interval in char array (exclusive)
     *
     * @return
     *  decoded value as a double
     */
    private static double decode_number(final char[] chars, int begin, int end)
    {
        double c_out = 0.0;
        int number_of_decimal_points = 0;
        boolean has_decimal_points = false;
        boolean invert = false;
        if (chars[begin] == '-')
        {
            invert=true;
            begin++;
        }
        else if (chars[begin] == '+')
        {
            begin++;
        }

        for (int i = begin; i < end; i++)
        {
            int n = char_is_number(chars[i]);
            if (n != -1)
            {
                c_out = c_out * 10 + n;
                if (has_decimal_points)
                {
                    number_of_decimal_points++;
                }
            }
            else if (char_is_decimal_point_divider(chars[i]))
            {
                has_decimal_points = true;
            }
            else
            {
                throw new AssertionError("Attempt to decode an unrecognized symbol as a number!");
            }
        }
        if (has_decimal_points)
        {
            c_out = c_out / Math.pow(10, number_of_decimal_points);
        }
        if (invert)
        {
            c_out = -c_out;
        }
        return c_out;
    }


    /**
     * Enables testing, if a single char is a number or not.
     *
     * @param c char to be tested
     *
     * @return
     * -> If char is a number from 0-9, returns char as int
     * -> If char is not a number, returns -1
     */
    private static int char_is_number(char c)
    {
        for (int i = 0; i < arr_numbers.length; i++)
        {
            if (c == arr_numbers[i])
            {
                return i;
            }
        }
        return -1;
    }


    /**
     *
     * @param c
     * @return
     */
    private static boolean char_is_decimal_point_divider(char c)
    {
        for (char sym_c: arr_number_decimal_symbols)
        {
            if (sym_c == c)
            {
                return true;
            }
        }
        return false;
    }


    private static CalculationType read_operator(char c)
    {
        switch (c) {
            case '+' -> {return CalculationType.ADDITION;}
            case '-' -> {return CalculationType.SUBTRACTION;}
            case '*' -> {return CalculationType.MULTIPLICATION;}
            case '/' -> {return CalculationType.DIVISION;}
            case '^' -> {return CalculationType.POWER;}
            case ')', '(' -> {return null;}
            ///TODO SUBTRACTION-METHOD this is an ugly solution. Find a better one?
            default -> {return null;}
            //default -> throw new AssertionError();
        }
    }
}
