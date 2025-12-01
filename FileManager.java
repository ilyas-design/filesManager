package com.filemanager;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class FileManager {
    public static void main(String[] args) {
        JFrame frame = new JFrame("File Manager");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Top panel: filename input and buttons
        JPanel topPanel = new JPanel();
        JTextField filenameField = new JTextField(25);
        JButton browseButton = new JButton("Browse");
        JButton readButton = new JButton("Read File");
        JButton writeButton = new JButton("Write File");
        topPanel.add(new JLabel("File:"));
        topPanel.add(filenameField);
        topPanel.add(browseButton);
        topPanel.add(readButton);
        topPanel.add(writeButton);

        // Center: text area for file content
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Browse button action
        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                filenameField.setText(selectedFile.getAbsolutePath());
            }
        });

        // Read button action
        readButton.addActionListener(e -> {
            String filename = filenameField.getText();
            if (filename.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please select a file first!");
                return;
            }
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                textArea.read(br, null);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error reading file: " + ex.getMessage());
            }
        });

        // Write button action
        writeButton.addActionListener(e -> {
            String filename = filenameField.getText();
            if (filename.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please select a file first!");
                return;
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
                textArea.write(bw);
                JOptionPane.showMessageDialog(frame, "File written successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error writing file: " + ex.getMessage());
            }
        });

        frame.setVisible(true);
    }
}
