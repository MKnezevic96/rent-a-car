package com.rent_a_car.agentski_bekend.dto;

import java.util.List;

public class KorpaDTO {
    String user;
    List<String> lista;

    public KorpaDTO() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<String> getLista() {
        return lista;
    }

    public void setLista(List<String> lista) {
        this.lista = lista;
    }
}
