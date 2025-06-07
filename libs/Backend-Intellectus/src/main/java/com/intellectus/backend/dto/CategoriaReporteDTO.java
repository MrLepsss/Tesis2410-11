package com.intellectus.backend.dto;

public class CategoriaReporteDTO {
    private String pregunta;

    public CategoriaReporteDTO() {
    }

    public CategoriaReporteDTO(String pregunta) {
        this.pregunta = pregunta;
    }

   public String getPregunta() {
       return pregunta;
   }
   public void setPregunta(String pregunta) {
       this.pregunta = pregunta;
   }
}
