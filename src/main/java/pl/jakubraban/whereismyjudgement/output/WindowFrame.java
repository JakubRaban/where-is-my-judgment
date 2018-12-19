package pl.jakubraban.whereismyjudgement.output;

import pl.jakubraban.whereismyjudgement.CommandHistoryHandler;
import pl.jakubraban.whereismyjudgement.functions.GetNewJudgmentsFunction;
import pl.jakubraban.whereismyjudgement.output.Console;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WindowFrame extends JFrame implements KeyListener {

    private JTextField inputField;
    private JTextArea outputField = new JTextArea();
    private String prompt = "?> ";
    private Console console;
    private CommandHistoryHandler historyHandler = new CommandHistoryHandler();

    public WindowFrame(Console console) {
        this.console = console;
        initializeInputField();
        initializeOutputField();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));
        setTitle("Where Is My Judgment");
        setVisible(true);
        add(inputField, BorderLayout.SOUTH);
    }

    private void initializeInputField() {
        inputField = new JTextField();
        inputField.setVisible(true);
        inputField.setBackground(Color.BLACK);
        inputField.setForeground(Color.white);
        inputField.setFont(new Font("Consolas", Font.PLAIN, 16));
        inputField.setCaretColor(Color.WHITE);
        inputField.setBorder(null);
        inputField.addKeyListener(this);
        inputField.setText(prompt);
    }

    private void initializeOutputField() {
        JScrollPane scrollPane = new JScrollPane(outputField);
        scrollPane.setSize(800,500);
        scrollPane.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE));
        outputField.setBackground(Color.BLACK);
        outputField.setForeground(Color.WHITE);
        outputField.setEditable(false);
        outputField.setFont(new Font("Consolas", Font.PLAIN, 14));
        outputField.setLineWrap(true);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void printMessage(String message) {
        outputField.append(message + "\n");
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER && GetNewJudgmentsFunction.everExecuted) {
            String typed = inputField.getText();
            String commandWithoutPrompt = typed.replace(prompt.trim(), "").trim();
            inputField.setText("");
            printMessage(typed);
            historyHandler.add(commandWithoutPrompt);
            inputField.setText(prompt);
            console.execute(commandWithoutPrompt);
        }
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            inputField.setText(prompt + historyHandler.previous());
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            inputField.setText(prompt + historyHandler.next());
        }
        if (inputField.getCaretPosition() < prompt.length()) {
            inputField.setText(prompt);
        }
        outputField.setCaretPosition(outputField.getDocument().getLength());
    }
}
