package com.brady.ui.owner;

import com.brady.entities.owner.OwnerDto;
import com.brady.entities.pet.PetDto;
import com.brady.service.owner.OwnerServiceRemote;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class OwnerEditDialog extends JDialog {
    private JTextField tfOwnerName;
    private JTextField tfPhoneNumber;
    private JButton btnUpdate;
    private JButton btnCancel;
    private OwnerDto owner;
    private final OwnerServiceRemote ownerService;

    public OwnerEditDialog(JFrame parent, PetDto pet, OwnerServiceRemote ownerService) {
        super(parent, "Update " + pet.getOwner().getName() + "'s Details", true);
        this.ownerService = ownerService;
        setSize(350, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        loadData(pet);
        initializeUserInterface();
        setupActionListeners();
    }

    private void loadData(PetDto pet) {
        try {
            owner = ownerService.getOwnerById(pet.getId());
        } catch (Exception ex) {
            showError("Could not load owner details");
            dispose();
        }
    }

    private void initializeUserInterface() {
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.tfOwnerName = new JTextField(this.owner.getName());
        this.tfPhoneNumber = new JTextField(this.owner.getTelephone());

        formPanel.add(new JLabel("Owner Name"));
        formPanel.add(this.tfOwnerName);
        formPanel.add(new JLabel("Phone Number (10 digits)"));
        formPanel.add(this.tfPhoneNumber);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.btnUpdate = new JButton("Update");
        this.btnCancel = new JButton("Cancel");
        buttonPanel.add(this.btnCancel);
        buttonPanel.add(this.btnUpdate);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupActionListeners() {
        this.btnUpdate.addActionListener(e -> onUpdate(owner));
        this.btnCancel.addActionListener(e -> dispose());
    }

    private void onUpdate(OwnerDto owner) {
        String name = this.tfOwnerName.getText().trim();
        String phone = this.tfPhoneNumber.getText().trim();

        if (name.isEmpty()) {
            showError("Name cannot be empty.");
            return;
        }

        if (!isValidPhone(phone)) {
            showError("Invalid phone number. Must be exactly 10 digits.");
            return;
        }

        try {
            owner.setName(name);
            owner.setTelephone(phone);
            this.ownerService.updateOwner(owner);
            JOptionPane.showMessageDialog(this, "Owner details updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            showError("Failed to update owner details");
        }
    }

    private boolean isValidPhone(String phone) {
        return Pattern.matches("\\d{10}", phone);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message,
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
