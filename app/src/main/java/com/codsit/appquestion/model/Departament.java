package com.codsit.appquestion.model;

public class Departament {
    private String id_dep;
    private String nombre;

    public Departament() {
    }

    public Departament(String id_dep, String nombre) {
        this.id_dep = id_dep;
        this.nombre = nombre;
    }

    public String getId_dep() {
        return id_dep;
    }

    public void setId_dep(String id_dep) {
        this.id_dep = id_dep;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
