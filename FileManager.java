package com.filemanager;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class FileManager {
    public static void main(String[] args) {
        JFrame frame = new JFrame("File Manager");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Top panel: filename input and buttons
        JPanel topPanel = new JPanel();
        JTextField filenameField = new JTextField(20);
        JButton readButton = new JButton("Read File");
        JButton writeButton = new JButton("Write File");
        topPanel.add(new JLabel("Filename:"));
        topPanel.add(filenameField);
        topPanel.add(readButton);
        topPanel.add(writeButton);

        // Center: text area for file content
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Read button action
        readButton.addActionListener(e -> {
            String filename = filenameField.getText();
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                textArea.read(br, null);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error reading file: " + ex.getMessage());
            }
        });

        // Write button action
        writeButton.addActionListener(e -> {
            String filename = filenameField.getText();
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
