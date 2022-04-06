import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class WelcomeScreen extends JFrame {

    public WelcomeScreen() {
        JPanel mainPanel = new JPanel();
        JPanel labelPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JButton easy = new JButton("Łatwy");
        easy.setSize(30, 20);
        JButton medium = new JButton("Średni");
        medium.setSize(30, 20);
        JButton hard = new JButton("Trudny");
        hard.setSize(30, 20);
        JButton nightmare = new JButton("Koszmar");
        nightmare.setSize(30, 20);
        JLabel jLabel = new JLabel("Wybierz poziom trudności");
        easy.addActionListener( (e) -> {
            this.dispose();
            try {
                new Model(1);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        medium.addActionListener( (e) -> {
            this.dispose();
            try {
                new Model(2);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        hard.addActionListener( (e) -> {
            this.dispose();
            try {
                new Model(3);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        nightmare.addActionListener( (e) -> {
            this.dispose();
            try {
               new Model(4);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        labelPanel.add(jLabel);
        buttonPanel.add(easy);
        buttonPanel.add(medium);
        buttonPanel.add(hard);
        buttonPanel.add(nightmare);
        mainPanel.add(labelPanel);
        mainPanel.add(buttonPanel);
        add(mainPanel);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 110);
        setVisible(true);
    }
}
