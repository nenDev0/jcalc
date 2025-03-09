package src.java.application;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Optional;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import src.java.functions.Graph;
import src.java.global.Config;


public class FunctionListElement
{

    private JPanel panel;
    private JButton remove;
    private JButton update;
    private JTextArea function_text;
    private int graph_id;
    private FunctionListElement self_reference;


    public FunctionListElement(String function, int id)
    {
        this.panel = new JPanel(new FlowLayout());
        this.graph_id = id;
        this.self_reference = this;

        function_text = new JTextArea(function);
        function_text.setPreferredSize(new Dimension(600, 24));
        function_text.setFont(Config.FONT_GLOBAL);
        remove = new JButton("x");
        remove.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                GraphRegister.get().remove_graph(graph_id);
                FunctionList.get().remove(self_reference);
            }
        });
        update = new JButton("update");
        update.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Optional<Graph> graph = GraphRegister.get().get_graph(graph_id);
                if (graph.isPresent())
                {
                    graph.get().set_function(function_text.getText());
                    GraphRegister.get().calculate_points(graph_id);
                }
            }
        });
        if (function != null)
        {
            GraphRegister.get().register_graph(function, id);
        }
        panel.add(update);
        panel.add(remove);
        panel.add(function_text);
    }


    public JPanel get_panel()
    {
        return panel;
    }

}
