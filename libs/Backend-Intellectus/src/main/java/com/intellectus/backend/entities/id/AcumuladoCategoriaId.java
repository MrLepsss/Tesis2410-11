package com.intellectus.backend.entities.id;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class AcumuladoCategoriaId implements Serializable {

    private Integer idCategoria;
    private Integer idConsulta;

    public AcumuladoCategoriaId() {
    }

    public AcumuladoCategoriaId(Integer idCategoria, Integer idConsulta) {
        this.idCategoria = idCategoria;
        this.idConsulta = idConsulta;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Integer getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Integer idConsulta) {
        this.idConsulta = idConsulta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof AcumuladoCategoriaId))
            return false;
        AcumuladoCategoriaId that = (AcumuladoCategoriaId) o;
        return Objects.equals(idCategoria, that.idCategoria) &&
                Objects.equals(idConsulta, that.idConsulta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCategoria, idConsulta);
    }
}