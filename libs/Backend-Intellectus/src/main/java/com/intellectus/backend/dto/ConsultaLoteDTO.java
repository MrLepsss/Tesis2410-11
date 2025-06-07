package com.intellectus.backend.dto;

import java.util.List;

public class ConsultaLoteDTO {

    private Integer consultaId;
    private List<Integer> ids;

    public ConsultaLoteDTO(Integer consultaId,List<Integer> ids)
    {
        this.consultaId=consultaId;
        this.ids=ids;
    }

    public void setConsultaId(Integer consultaId) {
        this.consultaId = consultaId;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Integer getConsultaId() {
        return consultaId;
    }

    public List<Integer> getIds() {
        return ids;
    }
}