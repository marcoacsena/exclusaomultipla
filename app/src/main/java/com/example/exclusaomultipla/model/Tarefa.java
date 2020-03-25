package com.example.exclusaomultipla.model;

public class Tarefa {

    private int id;
    public Integer image = null;
    private String nomeTarefa;
    public int color = -1;

    public Tarefa(int id, Integer image, String nomeTarefa, int color) {
        this.id = id;
        this.image = image;
        this.nomeTarefa = nomeTarefa;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public void setNomeTarefa(String nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
