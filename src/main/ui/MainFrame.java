package main.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {
    private DetailsPanel detailsPanel;
    private JTextArea textArea;
    private JButton textAreaClearButton;

    public MainFrame(String title) {
        super(title);

        // Set layout Manager
        setLayout(new BorderLayout());

        // Create Swing components
        textArea = new JTextArea();
        textAreaClearButton = new JButton("Clear");
        textAreaClearButton.setActionCommand("clear");
        textAreaClearButton.addActionListener(this);
        detailsPanel = new DetailsPanel();
        detailsPanel.addDetailListener(new DetailListener() {
            // MODIFIES: textArea
            // EFFECTS: prints out the text from event onto textArea
            public void detailEventOccured(DetailEvent event) {
                String text = event.getText();
                textArea.append(text);
            }
        });

        // Add Swing components to content pane
        Container c = getContentPane();

        c.add(textArea, BorderLayout.CENTER);
        c.add(textAreaClearButton, BorderLayout.SOUTH);
        c.add(detailsPanel, BorderLayout.WEST);

    }

    // MODIFIES: textArea
    // EFFECTS: when the textAreaClearButton is pressed, all the text in textArea is removed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("clear")) {
            textArea.setText("");
        }
    }
}
