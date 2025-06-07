package com.intellectus.backend.servicios;
import com.intellectus.backend.dto.RespuestaGraficoDTO;
import com.intellectus.backend.entities.Consulta;
import com.intellectus.backend.entities.ParteGrafico;
import com.intellectus.backend.entities.RespuestaGrafico;
import com.intellectus.backend.entities.id.RespuestaGraficoId;
import com.intellectus.backend.repositorios.ConsultaRepository;
import com.intellectus.backend.repositorios.ParteGraficoRepository;
import com.intellectus.backend.repositorios.RespuestaGraficoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RespuestaGraficoService {

    private final RespuestaGraficoRepository respuestaGraficoRepository;
    private final ConsultaRepository consultaRepository;
    private final ParteGraficoRepository parteGraficoRepository;
    public RespuestaGraficoService(RespuestaGraficoRepository respuestaGraficoRepository,
                                   ConsultaRepository consultaRepository,ParteGraficoRepository parteGraficoRepository) {
        this.respuestaGraficoRepository = respuestaGraficoRepository;
        this.consultaRepository = consultaRepository;
        this.parteGraficoRepository = parteGraficoRepository;
    }

    public ResponseEntity<String> guardarRespuestaGrafico(RespuestaGraficoDTO dto) {
        ParteGrafico parteGrafico = parteGraficoRepository.findById(dto.getIdParteGrafico()).orElse(null);
        Consulta consulta = consultaRepository.findById(dto.getIdConsulta()).orElse(null);
    
        if (parteGrafico == null || consulta == null) {
            return ResponseEntity.badRequest().body("Consulta o pregunta no encontrada. ID consulta: "
                    + dto.getIdConsulta() + ", ID pregunta: " + dto.getIdParteGrafico());
        }
    
        RespuestaGrafico existente = respuestaGraficoRepository
                .findByConsultaIdAndParteGraficoId(dto.getIdConsulta(), dto.getIdParteGrafico());
    
        if (existente == null) {
            RespuestaGrafico nueva = new RespuestaGrafico();
            nueva.setId(new RespuestaGraficoId(dto.getIdConsulta(), dto.getIdParteGrafico()));
            nueva.setConsulta(consulta);
            nueva.setParteGrafico(parteGrafico);
            nueva.setValor(dto.getValor());
            respuestaGraficoRepository.save(nueva);
        } else {
            if (existente.getValor() == null || !existente.getValor().equals(dto.getValor())) {
                existente.setValor(dto.getValor());
                respuestaGraficoRepository.save(existente);
            }
        }
    
        return ResponseEntity.ok("Respuesta Grafica guardada con éxito.");
    }
}