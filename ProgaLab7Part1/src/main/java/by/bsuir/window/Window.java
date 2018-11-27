package by.bsuir.window;

import by.bsuir.db.DataBaseWorker;
import by.bsuir.entities.Medicine;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.util.List;

public class Window extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel rootPanel;
    private JSpinner yearSpinner;
    private JSpinner issueSpinner;
    private JSpinner priceSpinner;
    private JTextField diseaseField;
    private JButton addButton;
    private JLabel nameLabel;
    private JLabel yearLabel;
    private JLabel issueLabel;
    private JPanel priceLabel;
    private JLabel diseaseLabel;
    private JTextField nameField;
    private JButton deleteButton;
    private JTextField nameFieldDel;
    private JLabel nameLabelDel;
    private JTextField nameFieldChange;
    private JTextField diseaseChange;
    private JSpinner yearChange;
    private JSpinner lifeChange;
    private JSpinner priceChange;
    private JButton findButtonChange;
    private JButton changeButtonChange;
    private JLabel nameLabelChange;
    private JLabel yearLebelChange;
    private JLabel lifeLabelChange;
    private JLabel priceLabelChange;
    private JLabel diseaseLabelChange;
    private JTable table;
    private JButton showButton;

    private DataBaseWorker dataBaseWorker;

    public Window() {
        super("Аптека");
        setSize(500, 350);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        dataBaseWorker = new DataBaseWorker();

        add(rootPanel);

        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String disease = diseaseField.getText();
            int year = (int) yearSpinner.getValue();
            int issue = (int) issueSpinner.getValue();
            BigDecimal price =
                    new BigDecimal((Double) priceSpinner.getValue()).setScale(2, BigDecimal.ROUND_HALF_DOWN);

            if (name.isEmpty() || disease.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Заполните все поля");
                return;
            }

            Medicine medicine = new Medicine(name, year, issue, price, disease);

            boolean result = dataBaseWorker.addRecord(medicine);

            if (result) {
                JOptionPane.showMessageDialog(this, "Препарат успешно добавлен");
                nameField.setText("");
                diseaseField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Препарат не добавлен");
            }


        });

        deleteButton.addActionListener(e -> {
            String name = nameFieldDel.getText();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Заполните поле");
                return;
            }

            boolean result = dataBaseWorker.deleteRecord(name);

            if (result) {
                JOptionPane.showMessageDialog(this, "Препарат успешно удалён");
                nameFieldDel.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Препарат не удалён");
            }
        });

        findButtonChange.addActionListener(e -> {
            changeButtonChange.setEnabled(false);
            String name = nameFieldChange.getText();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Заполните поле 'Название препарата'");
                return;
            }
            Medicine medicine = dataBaseWorker.findByName(name);

            if (medicine == null) {
                JOptionPane.showMessageDialog(this, "Препарат: '" + name + "' не найден");
                return;
            }

            yearChange.setValue(medicine.getIssueDate());
            lifeChange.setValue(medicine.getShelfLife());
            priceChange.setValue(medicine.getPrice());
            diseaseChange.setText(medicine.getDisease());

            changeButtonChange.setEnabled(true);

        });

        nameFieldChange.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changeButtonChange.setEnabled(false);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changeButtonChange.setEnabled(false);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changeButtonChange.setEnabled(false);
            }
        });

        changeButtonChange.addActionListener(e -> {
            String name = nameFieldChange.getText();
            String disease = diseaseChange.getText();
            int year = (int) yearChange.getValue();
            int issue = (int) lifeChange.getValue();
            BigDecimal price;
            if (priceChange.getValue() instanceof BigDecimal) {
                price = (BigDecimal) priceChange.getValue();
            } else {
                price =
                        new BigDecimal((Double) priceChange.getValue()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            }
            if (name.isEmpty() || disease.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Заполните все поля");
                return;
            }


            Medicine medicine = new Medicine(name, year, issue, price, disease);

            boolean result = dataBaseWorker.change(medicine);

            if (result) {
                JOptionPane.showMessageDialog(this, "Препарат успешно изменён");
                nameField.setText("");
                diseaseField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Препарат не изменён");
            }

            changeButtonChange.setEnabled(false);
        });

        showButton.addActionListener(e -> {
            List<Medicine> medicines = dataBaseWorker.getAll();
            if (medicines == null) {
                return;
            }
            DefaultTableModel tableModel = new DefaultTableModel(0, 5);

            Object[] headers = {"Название препарата", "Год выпуска", "Срок годности", "Цена", "Заболевание"};
            tableModel.addRow(headers);

            for (Medicine medicine : medicines) {
                String[] array = new String[5];
                array[0] = medicine.getName();
                array[1] = String.valueOf(medicine.getIssueDate());
                array[2] = String.valueOf(medicine.getShelfLife());
                array[3] = String.valueOf(medicine.getPrice());
                array[4] = medicine.getDisease();
                tableModel.addRow(array);
            }

            table.setModel(tableModel);
        });
    }

    private void createUIComponents() {
        yearSpinner = new JSpinner(new SpinnerNumberModel(2010, 2000, 2019, 1));
        issueSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1));
        priceSpinner = new JSpinner(new SpinnerNumberModel(5, 0.01, 1000, 0.01));

        yearChange = new JSpinner(new SpinnerNumberModel(2010, 2000, 2019, 1));
        lifeChange = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1));
        priceChange = new JSpinner(new SpinnerNumberModel(5, 0.01, 1000, 0.01));
    }
}
