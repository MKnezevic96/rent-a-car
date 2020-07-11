package com.admin_service.dto;

public class FuelTypeDTO {

    String name;
    boolean deleted;


    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public FuelTypeDTO() {
    }

    public FuelTypeDTO(String name, boolean deleted) {
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
