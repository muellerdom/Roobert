package GUI;


import javax.swing.*;
import java.awt.*;

class GeneralGUI {
    public static void main(String args[]) {
        JFrame frame = new JFrame("Chat Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem("Level 1");
        JMenuItem m12 = new JMenuItem("Level 2");
        JMenuItem m13 = new JMenuItem("Level 2");
        JMenuItem m14 = new JMenuItem("Level 2");
        JMenuItem m15 = new JMenuItem("Level 2");
        JMenuItem m16 = new JMenuItem("Level 2");
        JMenuItem m17 = new JMenuItem("Level 2");
        JMenuItem m18 = new JMenuItem("Level 2");
        JMenuItem m19 = new JMenuItem("Level 2");
        JMenuItem m20 = new JMenuItem("Level 2");

        m1.add(m11);
        m1.add(m11);
        m1.add(m11);
        m1.add(m11);
        m1.add(m11);
        m1.add(m11);
        m1.add(m11);

        m1.add(m20);
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter Text");
        JTextField tf = new JTextField(10);
        JButton send = new JButton("Send");
        JButton reset = new JButton("Reset");
        panel.add(label);
        panel.add(label);
        panel.add(tf);
        panel.add(send);
        panel.add(reset);
        JTextArea ta = new JTextArea();
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, ta);
        frame.setVisible(true);
    }
}
