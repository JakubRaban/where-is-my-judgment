package pl.jakubraban.whereismyjudgement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WindowFrame extends JFrame implements KeyListener {

    private JTextField promptField;
    private JTextField inputField;
    private JScrollPane outputArea;
    private JTextArea outputField;
    JTextArea txtNotes = new JTextArea();

    public WindowFrame() {
        inputField = new JTextField();
        inputField.setVisible(true);
        inputField.setBounds(30,685,970,30);
        inputField.setBackground(Color.BLACK);
        inputField.setForeground(Color.WHITE);
        inputField.setFont(new Font("Consolas", Font.PLAIN, 20));
        inputField.setBorder(null);
        inputField.addKeyListener(this);
        inputField.setText("?> ");
        JScrollPane scrollPane = new JScrollPane(txtNotes);
        scrollPane.setSize(800,500);
        txtNotes.setEnabled(false);
        txtNotes.setBackground(Color.BLACK);
        txtNotes.setForeground(Color.WHITE);
        txtNotes.setFont(new Font("Consolas", Font.PLAIN, 20));
        scrollPane.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.WHITE));
        add(scrollPane, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));
        setVisible(true);
        add(inputField, BorderLayout.SOUTH);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            String typed = inputField.getText();
            inputField.setText("");
            txtNotes.append(typed + "\n");
            inputField.setText("?> ");
        }
        if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(inputField.getCaretPosition() <= 3) {
                inputField.setText("?> ");
            }
        }
    }
}
