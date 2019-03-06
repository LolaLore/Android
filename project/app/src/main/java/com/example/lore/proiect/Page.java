package com.example.lore.proiect;

import java.util.List;

public class Page {
    private int number;
    private List<Car> cars;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
