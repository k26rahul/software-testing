package fileorganizer;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;

public class FileOrganizerApp extends JFrame {

  // Exposed for System Testing
  JButton organizeButton;
  JLabel statusLabel;
  JTextField pathInput;

  private final FileOrganizerCore core;

  public FileOrganizerApp() {
    core = new FileOrganizerCore();
    setupUI();
  }

  private void setupUI() {
    setTitle("System File Organizer");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new FlowLayout());
    setSize(400, 150);

    pathInput = new JTextField(20);
    pathInput.setText("C:/Temp/TestDir"); // Default path

    organizeButton = new JButton("Organize Now");
    statusLabel = new JLabel("Status: Awaiting input.");

    organizeButton.addActionListener(e -> {
      try {
        statusLabel.setText("Status: Processing...");
        boolean success = core.organizeDirectory(Paths.get(pathInput.getText()));
        if (success) {
          statusLabel.setText("Status: Organization Complete!");
        } else {
          statusLabel.setText("Status: Directory empty or invalid.");
        }
      } catch (Exception ex) {
        statusLabel.setText("Status: Error occurred.");
      }
    });

    add(new JLabel("Target Path:"));
    add(pathInput);
    add(organizeButton);
    add(statusLabel);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      new FileOrganizerApp().setVisible(true);
    });
  }
}
