package com.rent_a_car.agentski_bekend.model;

import java.util.List;

public class Korpa {
    User user;
    List<RentRequest> lista;

    public Korpa() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<RentRequest> getLista() {
        return lista;
    }

    public void setLista(List<RentRequest> lista) {
        this.lista = lista;
    }
}
