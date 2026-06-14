package fileorganizer;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Modern Swing-based user interface for the File Categorizer.
 * Displays the categorization summary in a scrollable text area.
 */
public class FileCategorizerApp extends JFrame {

  JButton categorizeButton;
  JLabel statusLabel;
  JTextField pathInput;
  JTextArea resultArea;

  private final FileCategorizerCore core;

  public FileCategorizerApp() {
    core = new FileCategorizerCore();
    setupUI();
  }

  private void setupUI() {
    setTitle("System File Categorizer");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout(10, 10));
    setSize(550, 450);
    setLocationRelativeTo(null);

    Color primaryColor = new Color(41, 128, 185);
    Color backgroundColor = new Color(245, 247, 250);
    Color textColor = new Color(44, 62, 80);

    JPanel topPanel = new JPanel();
    topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
    topPanel.setBackground(backgroundColor);

    pathInput = new JTextField(25);
    pathInput.setText("C:/Temp/TestDir");
    pathInput.setFont(new Font("SansSerif", Font.PLAIN, 13));

    categorizeButton = new JButton("Categorize Files");
    categorizeButton.setFont(new Font("SansSerif", Font.BOLD, 13));
    categorizeButton.setBackground(primaryColor);
    categorizeButton.setForeground(Color.WHITE);
    categorizeButton.setFocusPainted(false);

    statusLabel = new JLabel("Status: Awaiting input.");
    statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
    statusLabel.setForeground(textColor);

    topPanel.add(new JLabel("Target Path:"));
    topPanel.add(pathInput);
    topPanel.add(categorizeButton);
    topPanel.add(statusLabel);

    resultArea = new JTextArea();
    resultArea.setEditable(false);
    resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    resultArea.setBackground(Color.WHITE);
    resultArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JScrollPane scrollPane = new JScrollPane(resultArea);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Categorization Summary"));

    add(topPanel, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);

    categorizeButton.addActionListener(e -> {
      try {
        statusLabel.setText("Status: Processing...");
        resultArea.setText("");
        Path path = Paths.get(pathInput.getText());
        Map<String, List<String>> categorization = core.categorizeDirectory(path);
        String summary = core.generateSummary(categorization);
        resultArea.setText(summary);
        statusLabel.setText("Status: Categorization Complete!");
      } catch (Exception ex) {
        statusLabel.setText("Status: Error occurred.");
        resultArea.setText("Error: " + ex.getMessage());
      }
    });
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      new FileCategorizerApp().setVisible(true);
    });
  }
}
