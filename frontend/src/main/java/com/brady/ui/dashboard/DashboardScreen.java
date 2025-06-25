package com.brady.ui.dashboard;

import com.brady.entities.pet.PetDto;
import com.brady.entities.vaccination.VaccinationDto;
import com.brady.service.owner.OwnerServiceRemote;
import com.brady.service.pet.PetServiceRemote;
import com.brady.service.vaccination.VaccinationServiceRemote;
import com.brady.ui.owner.OwnerEditDialog;
import com.brady.ui.pet.PetEditDialog;
import com.brady.ui.vaccination.VaccineEditDialog;
import com.brady.utils.JNDIUtil;

import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DashboardScreen extends JFrame {

    private JTextField tfSearchField;
    private JButton btnSearchButton, btnEditPetButton, btnEditOwnerButton, btnEditVaccineButton;
    private JTable tbPet;
    private DefaultTableModel defaultTableModel;
    private PetServiceRemote petServiceRemote;
    private OwnerServiceRemote ownerServiceRemote;
    private VaccinationServiceRemote vaccinationServiceRemote;

    public DashboardScreen()  {
        init();
        setTitle("Pet Management Dashboard");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        initializeUserInterface();
        loadAllPets();
        setupActionListeners();
    }

    private void init() {
        try {
            this.petServiceRemote = JNDIUtil.lookupPetService();
            this.ownerServiceRemote = JNDIUtil.lookupOwnerService();
            this.vaccinationServiceRemote = JNDIUtil.lookupVaccinationService();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeUserInterface() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.tfSearchField = new JTextField(20);
        this.btnSearchButton = new JButton("Search");
        searchPanel.add(new JLabel("Search by Pet Name"));
        searchPanel.add(this.tfSearchField);
        searchPanel.add(this.btnSearchButton);
        add(searchPanel, BorderLayout.NORTH);

        this.defaultTableModel = new DefaultTableModel(
                new String[]{"Pet Name", "Age", "Last VaccinationEntity", "Owner Name"}, 0);
        this.tbPet = new JTable(this.defaultTableModel);
        JScrollPane scrollPane = new JScrollPane(this.tbPet);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.btnEditPetButton = new JButton("Update Pet Details");
        this.btnEditOwnerButton = new JButton("Update Owner Details");
        this.btnEditVaccineButton = new JButton("Update Vaccination Details");
        buttonPanel.add(this.btnEditPetButton);
        buttonPanel.add(this.btnEditOwnerButton);
        buttonPanel.add(this.btnEditVaccineButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupActionListeners() {
        this.btnSearchButton.addActionListener(e -> searchPets());
        this.btnEditPetButton.addActionListener(e -> editPet());
        this.btnEditOwnerButton.addActionListener(e -> editOwner());
        this.btnEditVaccineButton.addActionListener(e -> editVaccine());
    }

    private void loadAllPets() {
        try {
            List<PetDto> petDtos = this.petServiceRemote.getAllPets();
            this.populateTable(petDtos);
        } catch (Exception exception) {
            showError("Error while loading pets");
        }
    }

    private void populateTable(List<PetDto> pets) {
        defaultTableModel.setRowCount(0);
        for (PetDto pet : pets) {
            defaultTableModel.addRow(new Object[]{
                    pet.getName(),
                    pet.getAge(),
                    getLastVaccinationDate(pet),
                    pet.getOwner().getName()
            });
        }
    }

    private void searchPets() {
        try {
            String keyword = tfSearchField.getText().trim();
            List<PetDto> pets = petServiceRemote.searchPetsByName(keyword);
            populateTable(pets);
        } catch (Exception exception) {
            showError("Error while searching pets");
        }
    }

    private Object getLastVaccinationDate(PetDto pet) {
        Optional<VaccinationDto> optionalVaccination = pet.getVaccinations().stream().max(Comparator.comparing(VaccinationDto::getVaccinationDate));
        return optionalVaccination.<Object>map(vaccination -> vaccination.getVaccinationDate().toString()).orElse("No VaccinationEntity");
    }

    private PetDto getSelectedPet() {
        int row = this.tbPet.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a pet");
            return null;
        }

        String name = (String) this.defaultTableModel.getValueAt(row, 0);
        List<PetDto> pets = this.petServiceRemote.searchPetsByName(name); // assumes unique names or refine accordingly
        Optional<PetDto> petOption = pets.stream().filter((pet) -> pet.getName().equals(name)).findFirst();
        return petOption.orElse(null);
    }

    private void editPet() {
        PetDto selectedPet = getSelectedPet();
        if (selectedPet != null) {
            new PetEditDialog(this, selectedPet, petServiceRemote).setVisible(true);
            loadAllPets();
        }
    }

    private void editOwner() {
        PetDto selectedPet = getSelectedPet();
        if (selectedPet != null) {
            new OwnerEditDialog(this, selectedPet, this.ownerServiceRemote).setVisible(true);
            loadAllPets();
        }
    }

    private void editVaccine() {
        PetDto selectedPet = getSelectedPet();
        if (selectedPet != null) {
             new VaccineEditDialog(this, selectedPet, vaccinationServiceRemote)
                     .setVisible(true);
            loadAllPets();
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
                message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
