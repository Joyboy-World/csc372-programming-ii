
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class MenuFrame extends JFrame {
    private static final Path LOG_FILE = Path.of("log.txt");
    private static final DateTimeFormatter STAMP =
            DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy  h:mm:ss a");

    private final JTextArea textBox = new JTextArea(12, 40);
    private final JLabel status = new JLabel("Choose an option from the Options menu.");
    private final JPanel content = new JPanel(new BorderLayout(8, 8));


    private Color green = null;

    public MenuFrame() {
        super("CSC372 M3 - Menu Options");

        textBox.setLineWrap(true);
        textBox.setWrapStyleWord(true);

        content.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        content.add(new JScrollPane(textBox), BorderLayout.CENTER);
        content.add(status, BorderLayout.SOUTH);
        setContentPane(content);

        setJMenuBar(buildMenuBar());   // the menu bar has its own slot above the content pane

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private JMenuBar buildMenuBar() {
        JMenuItem dateItem = new JMenuItem("1. Show date and time");
        JMenuItem logItem = new JMenuItem("2. Write text box to log.txt");
        JMenuItem greenItem = new JMenuItem("3. Change background to green");
        JMenuItem exitItem = new JMenuItem("4. Exit");

        dateItem.addActionListener(e -> showDateTime());
        logItem.addActionListener(e -> writeLog());
        greenItem.addActionListener(e -> applyGreen());
        exitItem.addActionListener(e -> exitProgram());

        JMenu options = new JMenu("Options");
        options.add(dateItem);
        options.add(logItem);
        options.add(greenItem);
        options.addSeparator();
        options.add(exitItem);

        JMenuBar bar = new JMenuBar();
        bar.add(options);
        return bar;
    }

  
    private void showDateTime() {
        textBox.append(LocalDateTime.now().format(STAMP) + "\n");
        status.setText("Date and time added to the text box.");
    }


    private void writeLog() {
        String text = textBox.getText();
        if (text.isBlank()) {
            status.setText("Nothing written: the text box is empty.");
            JOptionPane.showMessageDialog(this, "The text box is empty, so nothing was written to log.txt.",
                    "Empty text box", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            Files.writeString(LOG_FILE, text + (text.endsWith("\n") ? "" : "\n"), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            status.setText("Text box written to " + LOG_FILE.toAbsolutePath());
        } catch (IOException ex) {
            status.setText("Could not write log.txt.");
            JOptionPane.showMessageDialog(this, "Could not write log.txt: " + ex.getMessage(),
                    "File error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void applyGreen() {
        if (green == null) {
            Random random = new Random();
            float hue = (90 + random.nextInt(61)) / 360f;
            float saturation = 0.55f + random.nextFloat() * 0.40f;
            float brightness = 0.55f + random.nextFloat() * 0.35f;
            green = Color.getHSBColor(hue, saturation, brightness);
        }
        content.setBackground(green);
        textBox.setBackground(green.brighter());
        String hex = String.format("#%02X%02X%02X", green.getRed(), green.getGreen(), green.getBlue());
        textBox.append("Background green: " + hex + "\n");
        status.setText("Background set to " + hex + " (same hue for this run).");
    }

    // Option 4: close the window and end the program.
    private void exitProgram() {
        dispose();
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuFrame().setVisible(true));
    }
}
