package com.knaujolimac.proyectomascotas.pojo;

public class Mascota {

    private int idMascota;
    private String nombreMascota;
    private int cantidadLikes;
    private int foto;

    public Mascota(){

    }

    public Mascota(int idMascota,int foto,String nombreMascota){
        this.idMascota = idMascota;
        this.nombreMascota = nombreMascota;
        this.foto = foto;
    }

    public Mascota(int foto,int cantidadLikes){
        this.cantidadLikes = cantidadLikes;
        this.foto = foto;
    }


    public Mascota(int idMascota,int foto,String nombreMascota,int cantidadLikes){
        this.idMascota = idMascota;
        this.nombreMascota = nombreMascota;
        this.foto = foto;
        this.cantidadLikes = cantidadLikes;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public int getCantidadLikes() {
        return cantidadLikes;
    }

    public void setCantidadLikes(int cantidadLikes) {
        this.cantidadLikes = cantidadLikes;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }
}
