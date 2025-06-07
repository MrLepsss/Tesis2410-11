package com.intellectus.backend.dto;

public class FilesDTO {

    private Integer id;
    private String nombreArchivo;
    private String tipoArchivo;

    public FilesDTO(Integer id, String nombreArchivo, String tipoArchivo) {
        this.id = id;
        this.nombreArchivo = nombreArchivo;
        this.tipoArchivo = tipoArchivo;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }
    public Integer getId() {
        return id;
    }
    public String getNombreArchivo() {
        return nombreArchivo;
    }
    public String getTipoArchivo() {
        return tipoArchivo;
    }
}