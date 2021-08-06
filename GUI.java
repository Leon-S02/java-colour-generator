import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.FileWriter;

public class GUI {
    public void initialiseGUI(int r, int g, int b){
        JFrame frame = new JFrame("Colours");
        JPanel panel = new JPanel();
        Font font = new Font("Arial", Font.BOLD, 22);
        JButton refresh = new JButton("New Colour");
        JButton save = new JButton("Save Colour");
        JLabel saved = new JLabel("");
        saved.setFont(font);
        saved.setForeground(Color.white);
        JTextArea text = new JTextArea();
        text.setColumns(24);
        text.setRows(6);
        JScrollPane scroll = new JScrollPane(text);
        panel.add(scroll);
        panel.add(refresh);
        panel.add(save);
        panel.add(saved);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,350);
        text.setFont(font);
        panel.setBackground(new Color(r,g,b));
        frame.add(panel);
        frame.setVisible(true);

        refresh.addActionListener(e -> {
            saved.setText("");
            var rgb = Colours.getColour();
            panel.setBackground(new Color(rgb.get(0),rgb.get(1),rgb.get(2)));
            String jointRGB = String.join(",",rgb.get(0).toString(),rgb.get(1).toString(),rgb.get(2).toString());
            String[] info = {};

            try{
                info = Colours.getColourInfo(jointRGB);
            } catch (InterruptedException | IOException interruptedException) {
                interruptedException.printStackTrace();
            }

            text.setText(info[0] + "\nRGB Value: " + jointRGB +"\nHex Value: " + info[1]);
        });

        save.addActionListener(e -> {
            try{
                FileWriter writer = new FileWriter("savedColours.txt",true);
                writer.write(text.getText());
                writer.write("\n\n");
                writer.close();
                System.out.println("Successfully saved colour info.");
                saved.setText("Colour saved successfully.");
            } catch (IOException ioException) {
                System.out.println("An error occurred");
                ioException.printStackTrace();
            }
        });
    }
}
