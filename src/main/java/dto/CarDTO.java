package dto;


import database.entity.Car;

import java.math.BigDecimal;

public class CarDTO {
    private int id;
    private String name;
    private BigDecimal cost;
    private Car.Status status;
    private Car.CarType category;
    private int passengers;

    public CarDTO() {
    }

    public CarDTO(String name, BigDecimal cost, Car.Status status, Car.CarType category, int passengers) {
        this.name = name;
        this.cost = cost;
        this.status = status;
        this.category = category;
        this.passengers = passengers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Car.Status getStatus() {
        return status;
    }

    public void setStatus(Car.Status status) {
        this.status = status;
    }

    public Car.CarType getCategory() {
        return category;
    }

    public void setCategory(Car.CarType category) {
        this.category = category;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }
}
