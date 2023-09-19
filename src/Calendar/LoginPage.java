package Calendar;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class LoginPage{

    //----
    public  JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private Map<String, String> userCredentials = new HashMap<>(); // Хранение данных пользователей
    private Map<String, List<DataBase>> Db = new HashMap<>(); // Хранение событий
    //----

    public static void main(String[] args){
        LoginPage lp = new LoginPage();
        DataBase.generateTestUsers();
        lp.createUI();
    }

    private void createLoginPage(){
        JPanel loginPanel= new JPanel();

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.TOP_ALIGNMENT);

        usernameField = new JTextField(20);
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));

        passwordField = new JPasswordField(20);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));

        loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginButton.addActionListener(e -> {
                    if (validateCredentials(usernameField.getText(), new String(passwordField.getPassword()))) {
                        loginPanel.setVisible(false);
                        new CalendarPage(frame, usernameField.getText());
                        frame.setSize(700,550);
                    }
                    else{
                        showWarning("Wrong Data!", "Please check your username and password");
                    }
                });
        //panel setup
        loginPanel.setLayout(new BoxLayout(loginPanel,BoxLayout.PAGE_AXIS));

        JPanel textPanel = new JPanel(); // greeting panel
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalGlue());
        textPanel.setBackground(Color.white);
        loginPanel.add(textPanel);

        loginPanel.add(Box.createRigidArea(new Dimension(5,0)));

        JPanel inputPanel = new JPanel(); // username and pass panel
        inputPanel.add(usernameField);
        inputPanel.add(Box.createRigidArea(new Dimension(5,0)));
        inputPanel.add(Box.createHorizontalGlue());
        inputPanel.add(passwordField);
            JPanel buttPanel = new JPanel();
            buttPanel.add(loginButton);
            buttPanel.setBackground(Color.WHITE);
        inputPanel.add(buttPanel);
        inputPanel.setBackground(Color.WHITE);

        loginPanel.add(inputPanel);
        loginPanel.setBackground(Color.WHITE);

        frame.setSize(700,200);
        frame.add(loginPanel);
    }

    //login&pass check
    private boolean validateCredentials(String username, String password) {
        return DataBase.userHM.containsKey(username) && password.equals(DataBase.userHM.get(username).password);
    }

    private void showWarning (String title, String message) {
        JOptionPane.showMessageDialog(frame,message,title,JOptionPane.ERROR_MESSAGE);
    }

    //Create form
    private void createUI() {

        frame = new JFrame("Meeting");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        createLoginPage();
        frame.setVisible(true);
    }
}
