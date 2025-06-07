package com.intellectus.backend.dto;

public class SeleccionTrastornoDTO {
    private Integer transtornoId;
    private Integer transtornoGeneralId;
    private Integer transtornoEspecificoId;

    public SeleccionTrastornoDTO(Integer transtornoId,Integer transtornoGeneralId,Integer transtornoEspecificoId)
    {
        this.transtornoId=transtornoId;
        this.transtornoGeneralId=transtornoGeneralId;
        this.transtornoEspecificoId=transtornoEspecificoId;
    }
    public Integer getTranstornoId() {
        return transtornoId;
    }

    public void setTranstornoId(Integer transtornoId) {
        this.transtornoId = transtornoId;
    }

    public Integer getTranstornoEspecificoId() {
        return transtornoEspecificoId;
    }

    public void setTranstornoEspecificoId(Integer transtornoEspecificoId) {
        this.transtornoEspecificoId = transtornoEspecificoId;
    }

    public Integer getTranstornoGeneralId() {
        return transtornoGeneralId;
    }

    public void setTranstornoGeneralId(Integer transtornoGeneralId) {
        this.transtornoGeneralId = transtornoGeneralId;
    }
}
