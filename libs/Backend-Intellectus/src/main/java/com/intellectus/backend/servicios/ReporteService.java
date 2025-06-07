package com.intellectus.backend.servicios;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.intellectus.backend.dto.AreaReporteDTO;
import com.intellectus.backend.dto.CategoriaAcumuladaDTO;
import com.intellectus.backend.dto.CategoriaPreguntasDTO;
import com.intellectus.backend.dto.GraficoReporteDTO;
import com.intellectus.backend.dto.ParteGraficoDTO;
import com.intellectus.backend.dto.PreguntaDTO;
import com.intellectus.backend.dto.PreguntaReporteDTO;
import com.intellectus.backend.dto.PreguntaValorFijoDTO;
import com.intellectus.backend.dto.ReporteDTO;
import com.intellectus.backend.dto.SeccionGraficoReporteDTO;
import com.intellectus.backend.entities.Area;
import com.intellectus.backend.entities.Categoria;
import com.intellectus.backend.entities.Consulta;
import com.intellectus.backend.entities.ConsultaMedico;
import com.intellectus.backend.entities.ParteGrafico;
import com.intellectus.backend.entities.Pregunta;
import com.intellectus.backend.entities.RespuestaAbierta;
import com.intellectus.backend.entities.RespuestaCerrada;
import com.intellectus.backend.entities.RespuestaAcumulado;
import com.intellectus.backend.entities.RespuestaGrafico;
import com.intellectus.backend.entities.SeccionGrafico;
import com.intellectus.backend.entities.TranstornoEspecificoConsulta;
import com.intellectus.backend.repositorios.AreaRepository;
import com.intellectus.backend.repositorios.CategoriaRepository;
import com.intellectus.backend.repositorios.ConsultaMedicoRepository;
import com.intellectus.backend.repositorios.ConsultaRepository;
import com.intellectus.backend.repositorios.RespuestaAbiertaRepository;
import com.intellectus.backend.repositorios.RespuestaCerradaRepository;
import com.intellectus.backend.repositorios.RespuestaAcumuladoRepository;
import com.intellectus.backend.repositorios.RespuestaGraficoRepository;
import com.intellectus.backend.repositorios.TranstornoEspecificoConsultaRepository;

@Service
public class ReporteService {

    private final AreaRepository areaRepository;
    private final ConsultaRepository consultaRepository;
    private final CategoriaRepository categoriaRepository;
    private final RespuestaAbiertaRepository respuestaAbiertaRepository;
    private final RespuestaCerradaRepository respuestaCerradaRepository;
    private final RespuestaAcumuladoRepository respuestaAcumuladoRepository;
    private final RespuestaGraficoRepository respuestaGraficoRepository;
    private final RespuestaAcumuladoService respuestaAcumuladoService;
    private final PreguntaService preguntaService;
    private final TranstornoEspecificoConsultaRepository transtornoEspecificoConsultaRepository;
    private final ConsultaMedicoRepository consultaMedicoRepository;

    public ReporteService(
            ConsultaRepository consultaRepository,
            CategoriaRepository categoriaRepository,
            RespuestaAbiertaRepository respuestaAbiertaRepository,
            RespuestaCerradaRepository respuestaCerradaRepository,
            RespuestaAcumuladoRepository respuestaAcumuladoRepository,
            RespuestaGraficoRepository respuestaGraficoRepository,
            AreaRepository areaRepository,
            RespuestaAcumuladoService respuestaAcumuladoService,
            PreguntaService preguntaService,
            TranstornoEspecificoConsultaRepository transtornoEspecificoConsultaRepository,
            ConsultaMedicoRepository consultaMedicoRepository) {
        this.areaRepository = areaRepository;
        this.consultaRepository = consultaRepository;
        this.categoriaRepository = categoriaRepository;
        this.respuestaAbiertaRepository = respuestaAbiertaRepository;
        this.respuestaCerradaRepository = respuestaCerradaRepository;
        this.respuestaAcumuladoRepository = respuestaAcumuladoRepository;
        this.respuestaGraficoRepository = respuestaGraficoRepository;
        this.respuestaAcumuladoService = respuestaAcumuladoService;
        this.preguntaService = preguntaService;
        this.transtornoEspecificoConsultaRepository = transtornoEspecificoConsultaRepository;
        this.consultaMedicoRepository = consultaMedicoRepository;
    }

    public ReporteDTO generarReporte(Integer consultaId) {
        Optional<Consulta> consultaOpt = consultaRepository.findById(consultaId);
        if (consultaOpt.isEmpty()) {
            return null;
        }
        ReporteDTO reporteDTO = new ReporteDTO();

        List<Area> areas = areaRepository.findAll();
        for (Area area : areas) {
            AreaReporteDTO areaReporteDTO = new AreaReporteDTO();
            areaReporteDTO.setNombre(area.getNombre());
            List<Categoria> categorias = categoriaRepository.findByAreaIdAndVisibilidadTrue(area.getId());
            for (Categoria categoria : categorias) {
                if (categoria.getTipoResultado() == null || categoria.getTipoResultado().isEmpty()) {
                    CategoriaPreguntasDTO categoriaPreguntasDTO = procesarCategoriaPreguntas(categoria,
                            consultaOpt.get());
                    if (!categoriaPreguntasDTO.getPreguntas().isEmpty()) {
                        areaReporteDTO.getCategorias().add(categoriaPreguntasDTO);
                    }
                } else if (!categoria.getTipoResultado().equals("Grafica")) {
                    CategoriaAcumuladaDTO categoriaAcumuladaDTO = procesarCategoriaAcumulada(categoria,
                            consultaOpt.get());
                    if (categoriaAcumuladaDTO != null) {
                        areaReporteDTO.getCategorias().add(categoriaAcumuladaDTO);
                    }
                } else {
                    GraficoReporteDTO graficoReporteDTO = procesarCategoriaGrafica(categoria, consultaOpt.get());
                    if (graficoReporteDTO != null) {
                        areaReporteDTO.getCategorias().add(graficoReporteDTO);
                    }
                }
            }
            if (!areaReporteDTO.getCategorias().isEmpty()) {
                reporteDTO.getAreas().add(areaReporteDTO);
            }
        }
        return reporteDTO;
    }

    private CategoriaPreguntasDTO procesarCategoriaPreguntas(Categoria categoria, Consulta consulta) {
        CategoriaPreguntasDTO categoriaPreguntasDTO = new CategoriaPreguntasDTO();
        categoriaPreguntasDTO.setPregunta(categoria.getNombre());
        List<Pregunta> preguntas = categoria.getPreguntas();

        if (categoria.getNombre().equals("Informacion Paciente")) {
            agregarPreguntaRespuesta(categoriaPreguntasDTO, "Fecha de evaluación",
                    consulta.getFechaEvaluacion().toString());
            agregarPreguntaRespuesta(categoriaPreguntasDTO, "Nombre", consulta.getPaciente().getNombre());
            agregarPreguntaRespuesta(categoriaPreguntasDTO, "Cedula", consulta.getPaciente().getCedula());
            agregarPreguntaRespuesta(categoriaPreguntasDTO, "Fecha de Nacimiento",
                    consulta.getPaciente().getFechaNacimiento().toString());
            agregarPreguntaRespuesta(categoriaPreguntasDTO, "Lateralidad", consulta.getPaciente().getLateralidad());
            agregarPreguntaRespuesta(categoriaPreguntasDTO, "Edad", consulta.getEdadPaciente().toString());
        }

        for (Pregunta pregunta : preguntas) {
            if (pregunta.getTipoRespuesta().equals("Texto") || pregunta.getTipoRespuesta().equals("Numero")) {
                RespuestaAbierta respuestasAbierta = respuestaAbiertaRepository
                        .findByConsultaIdAndPreguntaId(consulta.getId(), pregunta.getId());
                if (respuestasAbierta != null) {
                    agregarPreguntaRespuesta(categoriaPreguntasDTO, pregunta.getTextoPregunta(),
                            respuestasAbierta.getValor());
                }
            } else if (pregunta.getTipoRespuesta().equals("Desplegable")) {
                RespuestaCerrada respuestaCerrada = respuestaCerradaRepository
                        .findByConsultaIdAndPreguntaId(consulta.getId(), pregunta.getId());
                if (respuestaCerrada != null) {
                    agregarPreguntaRespuesta(categoriaPreguntasDTO, pregunta.getTextoPregunta(),
                            respuestaCerrada.getOpcion().getTexto());
                }
            } else if (pregunta.getTipoRespuesta().equals("Formula")) {
                PreguntaDTO respuesta = preguntaService.procesarPreguntaConFormula(pregunta, consulta.getId());
                PreguntaValorFijoDTO preguntaValorFijo = (PreguntaValorFijoDTO) respuesta;
                if (!preguntaValorFijo.getTipoRespuesta().equals("Error")) {
                    agregarPreguntaRespuesta(categoriaPreguntasDTO, preguntaValorFijo.getTextoPregunta(),
                            preguntaValorFijo.getValor());
                }
            } else {
                PreguntaDTO respuesta = preguntaService.procesarPreguntaConReferencia(pregunta, consulta.getId());
                PreguntaValorFijoDTO preguntaValorFijo = (PreguntaValorFijoDTO) respuesta;
                if (!preguntaValorFijo.getTipoRespuesta().equals("Error")) {
                    agregarPreguntaRespuesta(categoriaPreguntasDTO, preguntaValorFijo.getTextoPregunta(),
                            preguntaValorFijo.getValor());
                }
            }
        }
        if (categoria.getNombre().equals("Conclusiones generales")) {
            List<TranstornoEspecificoConsulta> transtornos = transtornoEspecificoConsultaRepository
                    .findByConsultaId(consulta.getId());
            Integer con = 1;
            for (TranstornoEspecificoConsulta transtorno : transtornos) {
                String transtornoNombre = transtorno.getTranstornoEspecifico().getNombre();
                agregarPreguntaRespuesta(categoriaPreguntasDTO, "Transtorno (" + con + ")", transtornoNombre);
                con++;
            }
            con = 1;
            List<ConsultaMedico> consultasMedico = consultaMedicoRepository
                    .findByConsultaId(consulta.getId());
            for (ConsultaMedico consultaMedico : consultasMedico) {
                String medicoNombre = consultaMedico.getMedico().getNombre();
                agregarPreguntaRespuesta(categoriaPreguntasDTO, "Medico (" + con + ")",
                        medicoNombre + " (" + consultaMedico.getMedico().getEspecialidad() + ")");
                con++;
            }
        }

        return categoriaPreguntasDTO;
    }

    private CategoriaAcumuladaDTO procesarCategoriaAcumulada(Categoria categoria, Consulta consulta) {
        CategoriaAcumuladaDTO categoriaAcumuladaDTO = new CategoriaAcumuladaDTO();
        categoriaAcumuladaDTO.setPregunta(categoria.getNombre());
        if (categoria.getTipoResultado().equals("Suma")) {
            respuestaAcumuladoService.calcularSuma(categoria, consulta);
        } else if (categoria.getTipoResultado().equals("contar")) {
            respuestaAcumuladoService.contar(categoria, consulta);
        } else {
            respuestaAcumuladoService.sumarIndividual(categoria, consulta);
        }
        RespuestaAcumulado respuestaAcumulado = respuestaAcumuladoRepository
                .findByConsultaIdAndCategoriaId(consulta.getId(), categoria.getId());
        if (respuestaAcumulado != null) {
            categoriaAcumuladaDTO.setRespuesta(respuestaAcumulado.getValor());
            return categoriaAcumuladaDTO;
        }
        return null;
    }

    private GraficoReporteDTO procesarCategoriaGrafica(Categoria categoria, Consulta consulta) {
        GraficoReporteDTO graficoReporteDTO = new GraficoReporteDTO();
        graficoReporteDTO.setPregunta(categoria.getGrafica().getNombre());
        graficoReporteDTO.setTipo(categoria.getGrafica().getTipo());
        for (SeccionGrafico seccion : categoria.getGrafica().getSecciones()) {
            SeccionGraficoReporteDTO seccionGrafico = new SeccionGraficoReporteDTO();
            seccionGrafico.setSeccion(seccion.getNombre());
            seccionGrafico.setColor(seccion.getColor());
            for (ParteGrafico parte : seccion.getPartesGrafico()) {
                PreguntaReporteDTO preguntaReporteDTO = new PreguntaReporteDTO();
                preguntaReporteDTO.setPregunta(parte.getTexto());
                if (parte.getValor().equals("Numero")) {
                    RespuestaGrafico respuestaGrafico = respuestaGraficoRepository
                            .findByConsultaIdAndParteGraficoId(consulta.getId(), parte.getId());
                    if (respuestaGrafico != null) {
                        preguntaReporteDTO.setRespuesta(respuestaGrafico.getValor());
                    }
                } else if (parte.getValor().equals("Formula")) {
                    ParteGraficoDTO parteGraficoDTO = preguntaService.procesarGraficoFormula(parte, consulta.getId());
                    if (!parteGraficoDTO.getTipoValor().equals("Error")) {
                        preguntaReporteDTO.setRespuesta(parteGraficoDTO.getValor());
                    }
                } else if (parte.getValor().equals("Referencia")) {
                    ParteGraficoDTO parteGraficoDTO = preguntaService.procesarGraficoReferencia(parte,
                            consulta.getId());
                    if (!parteGraficoDTO.getTipoValor().equals("Error")) {
                        preguntaReporteDTO.setRespuesta(parteGraficoDTO.getValor());
                    }
                }
                if (preguntaReporteDTO.getRespuesta() != null) {
                    seccionGrafico.getPartes().add(preguntaReporteDTO);
                }
            }
            if (!seccionGrafico.getPartes().isEmpty()) {
                graficoReporteDTO.getSecciones().add(seccionGrafico);
            }
        }
        if (graficoReporteDTO.getSecciones().isEmpty()) {
            return null;
        }

        return graficoReporteDTO;
    }

    private void agregarPreguntaRespuesta(CategoriaPreguntasDTO categoriaPreguntasDTO, String texto, String valor) {
        PreguntaReporteDTO preguntaReporteDTO = new PreguntaReporteDTO();
        preguntaReporteDTO.setPregunta(texto);
        preguntaReporteDTO.setRespuesta(valor);
        categoriaPreguntasDTO.getPreguntas().add(preguntaReporteDTO);
    }
}
