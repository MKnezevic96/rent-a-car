package com.rent_a_car.agentski_bekend.dto;

public class ManufacturerDTO {

    String name;
    boolean deleted;


    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public ManufacturerDTO() {
    }

    public ManufacturerDTO(String name, boolean deleted) {
        this.name = name;
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
