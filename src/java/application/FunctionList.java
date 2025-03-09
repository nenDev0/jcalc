package src.java.application;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import src.java.global.Config;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;


public final class FunctionList
{
    private JPanel panel;
    private JPanel new_function_container;
    private static int function_identifier_count;
    private JButton add_function;
    private JTextArea new_function;
    private LinkedList<FunctionListElement> ll_functions;
    private final static FunctionList self_instance = new FunctionList();


    private FunctionList()
    {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        new_function_container = new JPanel();
        new_function_container.setLayout(new BoxLayout(new_function_container, BoxLayout.X_AXIS));

        ll_functions = new LinkedList<FunctionListElement>();
        function_identifier_count = 0;

        new_function = new JTextArea(Config.EXAMPLE_FUNCTION);

        new_function.setFont(Config.FONT_GLOBAL);
        new_function.setPreferredSize(new Dimension(600, 24));
        add_function = new JButton("add function");
        add_function.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //TODO test, if new function follows syntax requirements somewhere...
                FunctionListElement element = new FunctionListElement(new_function.getText(), function_identifier_count);
                ll_functions.add(element);
                panel.add(element.get_panel());
                function_identifier_count++;
            }
        });
        new_function_container.add(new_function);
        new_function_container.add(add_function);
        panel.add(new_function_container);
    }


    public void remove(FunctionListElement element)
    {
        panel.remove(element.get_panel());
        ll_functions.remove(element);
    }


    public static FunctionList get()
    {
        return self_instance;
    }

    public JPanel get_panel()
    {
        return panel;
    }


}
