package com.brady.ui.pet;

import com.brady.entities.pet.PetDto;
import com.brady.service.pet.PetServiceRemote;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PetEditDialog extends JDialog {

    private JTextField tfName;
    private JTextField tfAge;
    private JButton btnUpdate;
    private JButton btnCancel;
    private final PetDto pet;
    private final PetServiceRemote petService;

    public PetEditDialog(JFrame parent, PetDto pet, PetServiceRemote petService) {
        super(parent, "Edit Pet Details - " + pet.getName(), true);
        this.pet = pet;
        this.petService = petService;
        setSize(350, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        initializeUserInterface();
        setupActionListeners();
    }

    private void initializeUserInterface() {
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.tfName = new JTextField(pet.getName());
        this.tfAge = new JTextField(String.valueOf(pet.getAge()));

        formPanel.add(new JLabel("Name"));
        formPanel.add(this.tfName);
        formPanel.add(new JLabel("Age"));
        formPanel.add(this.tfAge);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.btnUpdate = new JButton("Update");
        this.btnCancel = new JButton("Cancel");
        buttonPanel.add(this.btnCancel);
        buttonPanel.add(this.btnUpdate);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupActionListeners() {
        this.btnUpdate.addActionListener(this::onUpdate);
        this.btnCancel.addActionListener(e -> dispose());
    }

    private void onUpdate(ActionEvent actionEvent) {
        try {
            String newName = tfName.getText().trim();
            int newAge = Integer.parseInt(tfAge.getText().trim());
            this.pet.setName(newName);
            this.pet.setAge(newAge);
            this.petService.updatePet(this.pet);
            JOptionPane.showMessageDialog(this, "Pet details updated",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Failed to update pet details",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
