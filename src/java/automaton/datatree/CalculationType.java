package src.java.automaton.datatree;



public enum CalculationType
{

    POWER,
    MULTIPLICATION,
    DIVISION,
    ADDITION,
    SUBTRACTION;



    /**
     * A lower value means a higher binding strength.
     * If two values are the same, their binding order makes no difference.
     *
     * @param type
     *
     * @return int according to the binding strength of the CalculationType parameter
     */
    public static int binding_strength(CalculationType type)
    {
        switch (type)
        {
            case ADDITION, SUBTRACTION ->
            {
                return 2;
            }
            case MULTIPLICATION, DIVISION ->
            {
                return 1;
            }
            case POWER ->
            {
                return 0;
            }
            default -> throw new AssertionError();
        }
    }


    /**
     * Returns the character assigned to the CalculationType parameter.
     *
     * @param type
     * @throws AssertionError if there is no case for the given Type
     * (in this case CalculationType must be null)
     *
     * @return char {+,-,*,/,^}
     */
    public static char get_char(CalculationType type)
    {
        switch (type)
        {
            case ADDITION -> {return '+';}
            case SUBTRACTION -> {return '-';}
            case MULTIPLICATION -> {return '*';}
            case DIVISION -> {return '/';}
            case POWER -> {return '^';}
            default -> throw new AssertionError();
        }
    }


    public static int binding_strength_count()
    {
        return 3;
    }


    public static CalculationType[] binding_strength(int strength)
    {
        switch (strength)
        {
            case 0 ->
            {
                return new CalculationType[]{CalculationType.POWER};
            }
            case 1 ->
            {
                return new CalculationType[]{CalculationType.MULTIPLICATION, CalculationType.DIVISION};
            }
            case 2 ->
            {
                return new CalculationType[]{CalculationType.ADDITION, CalculationType.SUBTRACTION};
            }
            default -> throw new AssertionError();
        }
    }


}
