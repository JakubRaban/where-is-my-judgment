package pl.jakubraban.whereismyjudgement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WindowFrame extends JFrame implements KeyListener {

    private JTextField promptField = new JTextField("?> ");
    private JTextField inputField = new JTextField();
    private JTextArea outputField = new JTextArea(20,20);

    public WindowFrame() {
        setSize(1000, 750);
        setVisible(true);
        setResizable(false);
        addKeyListener(this);
        setTitle("Where Is My Judgment");

        promptField.setVisible(true);
        promptField.setBounds(0, 685, 30, 30);
        promptField.setBackground(Color.BLACK);
        promptField.setForeground(Color.WHITE);
        promptField.setFont(new Font("Consolas", Font.PLAIN, 20));
        promptField.setEnabled(false);
        promptField.setBorder(null);
        add(promptField);

        inputField.setVisible(true);
        inputField.setBounds(30,685,970,30);
        inputField.setBackground(Color.BLACK);
        inputField.setForeground(Color.WHITE);
        inputField.setFont(new Font("Consolas", Font.PLAIN, 20));
        inputField.setBorder(null);
        inputField.addKeyListener(this);
        add(inputField);

        outputField.setVisible(true);
        outputField.setBackground(Color.BLACK);
        outputField.setEnabled(false);
        outputField.setLineWrap(true);
        outputField.setFont(new Font("Consolas", Font.PLAIN, 20));
        add(outputField);

    }

    public JTextArea getOutputField() {
        return outputField;
    }

    public JTextArea getInputField() {
        return outputField;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == 10) {
            String typed = inputField.getText();
            inputField.setText("");
            outputField.append(typed + "\n");
        }
    }
}
