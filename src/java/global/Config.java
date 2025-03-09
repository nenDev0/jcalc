package src.java.global;

import java.awt.Color;
import java.awt.Font;

public final class Config
{

    public static final Font FONT_GLOBAL = new Font("global", 0, 20);
    public static final Color BACKGROUND_COLOR = new Color(20,22,25);
    public static final Color GRAPH_VIEWPORT_COLOR = new Color(12, 12, 12);
    public static final Color GRAPH_VIEWPORT_DRAWING_COLOR = new Color(255, 255, 255);

    public static final boolean LOG_CALCULATION_STATE = false;

    public static final int GRAPH_VIEWPORT_MAX_WIDTH = 100;
    public static final int GRAPH_VIEWPORT_MAX_HEIGHT = 100;
    public static final String EXAMPLE_FUNCTION = "(-1) * x^(1/2)";
}
