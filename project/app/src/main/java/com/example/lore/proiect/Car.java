package com.example.lore.proiect;

public class Car {
    private Integer id;
    private String brand;
    private String horsePower;
    private Double maxSpeed;
    private Integer manufacturingYear;
    Boolean isChecked;

    @Override
    public String toString() {
        return        id +
                "," + brand +
                "," + horsePower +
                "," + maxSpeed +
                "," + manufacturingYear +
                "," + isChecked + ";"
                ;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public Car(Integer id, String brand, String horsePower, Double maxSpeed, Integer manufacturingYear, Boolean isChecked) {

        this.id = id;
        this.brand = brand;
        this.horsePower = horsePower;
        this.maxSpeed = maxSpeed;
        this.manufacturingYear = manufacturingYear;
        this.isChecked = isChecked;
    }

    public Car(Integer id, String brand, String horsePower, Double maxSpeed, Integer manufacturingYear) {
        this.id = id;
        this.brand = brand;
        this.horsePower = horsePower;
        this.maxSpeed = maxSpeed;
        this.manufacturingYear = manufacturingYear;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(String horsePower) {
        this.horsePower = horsePower;
    }

    public Double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Integer getManufacturingYear() {
        return manufacturingYear;
    }

    public void setManufacturingYear(Integer manufacturingYear) {
        this.manufacturingYear = manufacturingYear;
    }

    public Car() {

    }
}
