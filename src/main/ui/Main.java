package main.ui;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            // MODIFIES: frame
            // EFFECTS: creates a GUI and then runs it
            @Override
            public void run() {
                JFrame frame = new MainFrame("Shopping List");
                frame.setSize(600,  500);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });

    }
}


