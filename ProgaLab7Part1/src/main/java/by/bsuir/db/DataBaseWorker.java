package by.bsuir.db;

import by.bsuir.entities.Medicine;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseWorker {
    private static final String URL = "jdbc:mysql://localhost:3306/psp_lab7_1?autoReconnect=true&useSSL=false";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "MySQL18662507";
    private static Connection connection;


    public DataBaseWorker() {
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addRecord(Medicine medicine) {
        try (Statement statement = connection.createStatement()) {
            if (!connection.isClosed()) {
                if (isPrimaryExist(medicine.getName())) {
                    return false;
                }
                System.out.println(statement.executeUpdate("insert into medicine (name, issue_year, shelf_life, price, disease)\n" +
                        "values ('" + medicine.getName() + "',\n" +
                        "        " + medicine.getIssueDate() + ",\n" +
                        "        '" + medicine.getShelfLife() + "',\n" +
                        "        " + medicine.getPrice() + ",\n" +
                        "        '" + medicine.getDisease() + "')")

                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteRecord(String name) {
        try (Statement statement = connection.createStatement()) {
            if (!connection.isClosed()) {
                if (!isPrimaryExist(name)) {
                    return false;
                }
                int changedRows = statement.executeUpdate("delete \n" +
                        "from medicine \n" +
                        "where(name = '" + name + "')");

                if (changedRows == 1) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean isPrimaryExist(String key) {
        try (Statement statement = connection.createStatement()) {
            if (!connection.isClosed()) {
                ResultSet resultSet = statement.executeQuery("select * from medicine where name = '" + key + "';");
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Medicine findByName(String key) {
        try (Statement statement = connection.createStatement()) {
            if (!connection.isClosed()) {
                ResultSet resultSet = statement.executeQuery("select * from medicine where name = '" + key + "';");
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String disease = resultSet.getString("disease");
                    int year = resultSet.getInt("issue_year");
                    int issue = resultSet.getInt("shelf_life");
                    BigDecimal price = resultSet.getBigDecimal("price");
                    Medicine medicine = new Medicine(name, year, issue, price, disease);
                    return medicine;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean change(Medicine medicine) {
        try (Statement statement = connection.createStatement()) {
            if (!connection.isClosed()) {
                ResultSet resultSet = statement.executeQuery("select * from medicine where name = '" + medicine.getName() + "';");
                if (!resultSet.next()) {
                    return false;
                }
                int changedRows = statement.executeUpdate("update medicine \n" +
                        "set issue_year='" + medicine.getIssueDate() + "', \n" +
                        "    shelf_life='" + medicine.getShelfLife() + "',\n" +
                        "    price='" + medicine.getPrice() + "', \n" +
                        "    disease='" + medicine.getDisease() + "' \n" +
                        "where name= '" + medicine.getName() + "';");
                if (changedRows == 1) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Medicine> getAll() {
        try (Statement statement = connection.createStatement()) {
            if (!connection.isClosed()) {
                List<Medicine> medicines = new ArrayList<>();
                ResultSet resultSet = statement.executeQuery("select * from medicine;");
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String disease = resultSet.getString("disease");
                    int year = resultSet.getInt("issue_year");
                    int issue = resultSet.getInt("shelf_life");
                    BigDecimal price = resultSet.getBigDecimal("price");
                    Medicine medicine = new Medicine(name, year, issue, price, disease);
                    medicines.add(medicine);
                }
                return medicines;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
