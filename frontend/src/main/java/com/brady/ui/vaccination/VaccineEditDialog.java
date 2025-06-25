package com.brady.ui.vaccination;

import com.brady.entities.pet.PetDto;
import com.brady.entities.vaccination.VaccinationDto;
import com.brady.service.vaccination.VaccinationServiceRemote;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VaccineEditDialog extends JDialog {

    private VaccinationDto vaccine;
    private final VaccinationServiceRemote vaccinationServiceRemote;
    private JTextField tfDescription;
    private JTextField tfDate;
    private JButton btnUpdate;
    private JButton btnCancel;

    public VaccineEditDialog(JFrame parent, PetDto pet, VaccinationServiceRemote vaccinationServiceRemote) {
        super(parent, "Update Vaccination Details", true);
        this.vaccinationServiceRemote = vaccinationServiceRemote;
        loadData(pet.getId());
        setSize(350, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        initializeUserInterface();
        setupActionListeners();
    }

    private void loadData(Long id) {
        this.vaccine = this.vaccinationServiceRemote.getVaccinationByPetId(id);
    }

    private void initializeUserInterface() {
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.tfDescription = new JTextField(this.vaccine.getVaccineDescription());
        this.tfDate = new JTextField(this.vaccine.getVaccinationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        formPanel.add(new JLabel("Description"));
        formPanel.add(this.tfDescription);

        formPanel.add(new JLabel("Vaccination Date"));
        formPanel.add(tfDate);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.btnCancel = new JButton("Cancel");
        this.btnUpdate = new JButton("Update");
        buttonPanel.add(this.btnCancel);
        buttonPanel.add(this.btnUpdate);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupActionListeners() {
        this.btnUpdate.addActionListener(this::onUpdate);
        this.btnCancel.addActionListener(e -> dispose());
    }

    private void onUpdate(ActionEvent e) {
        try {
            String description = this.tfDescription.getText().trim();
            String dateInStringFormat = this.tfDate.getText().trim();

            LocalDateTime date = LocalDateTime.parse(dateInStringFormat, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            if (date.isAfter(LocalDateTime.now())) {
                JOptionPane.showMessageDialog(this,
                        "Vaccination date cannot be in the future.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.vaccine.setVaccineDescription(description);
            this.vaccine.setVaccinationDate(date);

            this.vaccinationServiceRemote.updateVaccination(this.vaccine);
            JOptionPane.showMessageDialog(this,
                    "Vaccination updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Failed to update vaccination",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
