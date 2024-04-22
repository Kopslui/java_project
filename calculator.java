import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class calculator extends JFrame implements ActionListener {

    private JTextField textField;
    private String operand1, operator, operand2;
    private String[] popupMessages = {
            "Do you like me?",
            "Am I interesting?",
            "Using me makes you happy?",
            "I am the best?",
            "Did you miss me?",
            "You don't have any other calculator!! Right?"
    };

    public calculator() {
        operand1 = operator = operand2 = "";
        setupUI();
    }

    private void setupUI() {
        // Create frame
        JFrame frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create textfield
        textField = new JTextField(16);
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.RIGHT);

        // Create buttons
        JButton[] buttons = new JButton[]{
                new JButton("9"), new JButton("8"), new JButton("7"), new JButton("/"),
                new JButton("6"), new JButton("5"), new JButton("4"), new JButton("*"),
                new JButton("3"), new JButton("2"), new JButton("1"), new JButton("-"),
                new JButton("C"), new JButton("0"), new JButton("="), new JButton("+")
        };

        // Add action listeners to buttons
        for (JButton button : buttons) {
            button.addActionListener(this);
        }

        // Create panel
        JPanel panel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(4, 4)); // 4 rows, 4 columns

        // Add buttons to button panel
        for (JButton button : buttons) {
            buttonPanel.add(button);
        }

        // Add components to panel
        panel.add(textField, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        // Add panel to frame
        frame.add(panel);
        frame.setSize(250, 250);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if (Character.isDigit(actionCommand.charAt(0))) {
            if (!operator.isEmpty())
                operand2 += actionCommand;
            else
                operand1 += actionCommand;
            textField.setText(operand1 + operator + operand2);
        } else if (actionCommand.equals("C")) {
            operand1 = operator = operand2 = "";
            textField.setText("");
        } else if (actionCommand.equals("=")) {
            double result;
            switch (operator) {
                case "+":
                    result = Double.parseDouble(operand1) + Double.parseDouble(operand2);
                    break;
                case "-":
                    result = Double.parseDouble(operand1) - Double.parseDouble(operand2);
                    break;
                case "*":
                    result = Double.parseDouble(operand1) * Double.parseDouble(operand2);
                    break;
                case "/":
                    if (Double.parseDouble(operand2) == 0) {
                        textField.setText("Error: Division by zero");
                        return;
                    }
                    result = Double.parseDouble(operand1) / Double.parseDouble(operand2);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operator");
            }

            // Show random popup message
            String message = popupMessages[new Random().nextInt(popupMessages.length)];
            int choice = JOptionPane.showConfirmDialog(this, message, "Confirmation", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                textField.setText(operand1 + operator + operand2 + "=" + result);
            } else {
                textField.setText("Do it by yourself :(");
            }

            operand1 = Double.toString(result);
            operand2 = "";
            operator = "";
        } else {
            if (!operator.isEmpty()) {
                actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "="));
                operand1 = textField.getText().substring(0, textField.getText().indexOf("=") + 1);
            }
            operator = actionCommand;
            textField.setText(operand1 + operator);
        }
    }

    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and run the calculator
        new calculator();
    }
}
