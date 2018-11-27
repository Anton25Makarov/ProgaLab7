package by.bsuir.entities;

import java.math.BigDecimal;

public class Medicine {
    private String name;
    private int issueDate;
    private int shelfLife;
    private BigDecimal price;
    private String disease;

    public Medicine(String name, int issueDate, int shelfLife, BigDecimal price, String disease) {
        this.name = name;
        this.issueDate = issueDate;
        this.shelfLife = shelfLife;
        this.price = price;
        this.disease = disease;
    }

    public Medicine() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(int issueDate) {
        this.issueDate = issueDate;
    }

    public int getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(int shelfLife) {
        this.shelfLife = shelfLife;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }
}
