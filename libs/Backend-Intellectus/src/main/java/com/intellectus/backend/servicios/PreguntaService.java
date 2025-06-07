package com.intellectus.backend.servicios;

import org.springframework.stereotype.Service;
import com.intellectus.backend.auxiliar.Comparador;
import com.intellectus.backend.auxiliar.FormulaProcessor;
import com.intellectus.backend.dto.GraficoDTO;
import com.intellectus.backend.dto.OpcionListadoDTO;
import com.intellectus.backend.dto.ParteGraficoDTO;
import com.intellectus.backend.dto.PreguntaConOpcionesDTO;
import com.intellectus.backend.dto.PreguntaDTO;
import com.intellectus.backend.dto.PreguntaSinOpcionesDTO;
import com.intellectus.backend.dto.PreguntaValorFijoDTO;
import com.intellectus.backend.dto.RespuestaAbiertaDTO;
import com.intellectus.backend.dto.RespuestaGraficoDTO;
import com.intellectus.backend.dto.SeccionGraficoDTO;
import com.intellectus.backend.entities.Categoria;
import com.intellectus.backend.entities.Grafica;
import com.intellectus.backend.entities.OpcionListado;
import com.intellectus.backend.entities.ParteGrafico;
import com.intellectus.backend.entities.Pregunta;
import com.intellectus.backend.entities.RespuestaAbierta;
import com.intellectus.backend.entities.RespuestaCerrada;
import com.intellectus.backend.entities.RespuestaGrafico;
import com.intellectus.backend.entities.SeccionGrafico;
import com.intellectus.backend.entities.Valor;
import com.intellectus.backend.repositorios.CategoriaRepository;
import com.intellectus.backend.repositorios.ConsultaRepository;
import com.intellectus.backend.repositorios.GraficaRepository;
import com.intellectus.backend.repositorios.ParteGraficoRepository;
import com.intellectus.backend.repositorios.PreguntaRepository;
import com.intellectus.backend.repositorios.RespuestaAbiertaRepository;
import com.intellectus.backend.repositorios.RespuestaGraficoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

@Service
public class PreguntaService {

    private final PreguntaRepository preguntaRepository;
    private final RespuestaAbiertaRepository respuestaAbiertaRepository;
    private final RespuestaAbiertaService respuestaAbiertaService;
    private final CategoriaRepository categoriaRepository;
    private final RespuestaGraficoRepository respuestaGraficoRepository;
    private final ParteGraficoRepository parteGraficoRepository;
    private final RespuestaGraficoService respuestaGraficoService;
    private final GraficaRepository graficaRepository;

    @Autowired
    public PreguntaService(PreguntaRepository preguntaRepository,
            RespuestaAbiertaRepository respuestaAbiertaRepository, ConsultaRepository consultaRepository,
            CategoriaRepository categoriaRepository, RespuestaGraficoRepository respuestaGraficoRepository,
            ParteGraficoRepository parteGraficoRepository,GraficaRepository graficaRepository) {
        this.preguntaRepository = preguntaRepository;
        this.respuestaAbiertaRepository = respuestaAbiertaRepository;
        this.categoriaRepository = categoriaRepository;
        this.respuestaGraficoRepository = respuestaGraficoRepository;
        this.parteGraficoRepository = parteGraficoRepository;
        this.graficaRepository = graficaRepository;
        this.respuestaGraficoService = new RespuestaGraficoService(respuestaGraficoRepository, consultaRepository,
                parteGraficoRepository);

        this.respuestaAbiertaService = new RespuestaAbiertaService(respuestaAbiertaRepository, preguntaRepository,
                consultaRepository);
    }

    public List<PreguntaDTO> obtenerPreguntasPorCategoria(Integer idCategoria, Integer idConsulta) {

        Optional<Categoria> categoriaOptional = this.categoriaRepository.findById(idCategoria);
        List<Pregunta> preguntas = preguntaRepository.findByCategoriaIdConCategoria(idCategoria);
        List<PreguntaDTO> resultado = new ArrayList<>();

        if (categoriaOptional.isPresent()) {
            String tipoConsulta = categoriaOptional.get().getTipoResultado();
            if (tipoConsulta != null && (tipoConsulta.equals("Grafica") || tipoConsulta.equals("Grafica-Fija"))) {
                resultado.add(procesarGrafico(categoriaOptional.get(), idConsulta));
            }
            for (Pregunta p : preguntas) {
                if (p.isVisibilidad()) {
                    if (p.getPreguntaListado() != null) {
                        resultado.add(procesarPreguntaConOpciones(p, idConsulta));
                    } else if (p.getPreguntaReferencia() != null) {
                        resultado.add(procesarPreguntaConReferencia(p, idConsulta));
                    } else if (p.getPreguntaFormula() != null) {
                        resultado.add(procesarPreguntaConFormula(p, idConsulta));
                    } else {
                        resultado.add(procesarPreguntaSinOpciones(p, idConsulta));
                    }
                }
            }
        }
        return resultado;
    }

    private GraficoDTO procesarGrafico(Categoria categoria, Integer idConsulta) {

        Optional<Grafica> graficoOptional = graficaRepository.findById(categoria.getGrafica().getId());
        if (!graficoOptional.isPresent()) {
            return null;
        }
        Grafica grafico = graficoOptional.get();
        GraficoDTO graficoDTO = new GraficoDTO(grafico.getId(), grafico.getNombre(), "grafica", categoria.getId());
        graficoDTO.setTipo(grafico.getTipo());
        List<SeccionGraficoDTO> secciones = new ArrayList<>();
        for (SeccionGrafico seccion : grafico.getSecciones()) {
            SeccionGraficoDTO seccionDTO = new SeccionGraficoDTO(seccion.getNombre(), seccion.getColor());
            List<ParteGraficoDTO> parte = new ArrayList<>();
            for (ParteGrafico parteGrafico : seccion.getPartesGrafico()) {
                if (parteGrafico.getValor() != null && parteGrafico.getValor().equals("Referencia")) {
                    parte.add(procesarGraficoReferencia(parteGrafico, idConsulta));
                } else if (parteGrafico.getValor() != null && parteGrafico.getValor().equals("Formula")) {
                    parte.add(procesarGraficoFormula(parteGrafico, idConsulta));
                } else {
                    String valor=null;
                    Optional<RespuestaGrafico> respuestaGrafico = parteGrafico.getRespuestasGrafico().stream()
                            .filter(r -> r.getConsulta().getId().equals(idConsulta)).findFirst();
                    if (respuestaGrafico.isPresent()) {
                        valor= respuestaGrafico.get().getValor();
                    }
                    parte.add(new ParteGraficoDTO(parteGrafico.getId(), parteGrafico.getTexto(),
                            parteGrafico.getValor(), valor));
                }
            }
            seccionDTO.setPartes(parte);
            secciones.add(seccionDTO);
        }
        graficoDTO.setSecciones(secciones);
        return graficoDTO;
    }

    public ParteGraficoDTO procesarGraficoFormula(ParteGrafico parteGrafico, Integer idConsulta) {
        ParteGraficoDTO dto = new ParteGraficoDTO();
        dto.setId(parteGrafico.getId());
        dto.setTexto(parteGrafico.getTexto());
        String formula = parteGrafico.getGraficaFormula().getFormula();
        String origen = parteGrafico.getGraficaFormula().getOrigen();
        FormulaProcessor formulaProcessor = new FormulaProcessor(this.respuestaAbiertaRepository,
                this.preguntaRepository, respuestaGraficoRepository, parteGraficoRepository);
        try {
            Double resultado = formulaProcessor.procesarFormula(formula, idConsulta, origen);
            if (resultado != null) {
                dto.setTipoValor("Fijo");
                dto.setValor(resultado.toString());
                this.respuestaGraficoService.guardarRespuestaGrafico(
                        new RespuestaGraficoDTO(idConsulta, parteGrafico.getId(), resultado.toString()));
            } else {
                dto.setTipoValor("Error");
                dto.setValor("No se encontró respuesta para la fórmula.");
            }
            return dto;
        } catch (Exception e) {
            dto.setTipoValor("Error-Temporal");
            dto.setValor(e.getMessage());
            return dto;
        }
    }

    public ParteGraficoDTO procesarGraficoReferencia(ParteGrafico parteGrafico, Integer idConsulta) {
        ParteGraficoDTO dto = new ParteGraficoDTO();
        dto.setId(parteGrafico.getId());
        dto.setTexto(parteGrafico.getTexto());
        String tipoReferencia = parteGrafico.getGraficoReferencia().getReferencia().getTipo();
        if ("Fijo".equals(tipoReferencia)) {
            dto.setTipoValor("Fijo");
            for (Valor valor : parteGrafico.getGraficoReferencia().getReferencia().getValores()) {
                dto.setValor(valor.getValorCondicional());
            }
            return dto;
        } else if ("Lista".equals(tipoReferencia)
                && "cerrada".equals(parteGrafico.getGraficoReferencia().getReferencia().getOrigen())) {
            Pregunta preguntaReferenciaDatos = parteGrafico.getGraficoReferencia().getReferencia()
                    .getValorTomarPregunta();
            Optional<RespuestaCerrada> respuestaCerrada = preguntaReferenciaDatos
                    .getRespuestasCerradas().stream().filter(r -> r.getPregunta().getId()
                            .equals(preguntaReferenciaDatos.getId()) &&
                            r.getConsulta().getId().equals(idConsulta))
                    .findFirst();
            if (!respuestaCerrada.isPresent()) {
                dto.setTipoValor("Error");
                dto.setValor("No se encontró respuesta. Por favor llenar primero la pregunta: " +
                        preguntaReferenciaDatos.getTextoPregunta() + " en " +
                        preguntaReferenciaDatos.getCategoria().getNombre());
                return dto;
            }
            return procesarGraficoReferenciaLista(parteGrafico, respuestaCerrada.get().getOpcion().getValor(), dto);
        } else {
            dto.setTipoValor("Error");
            dto.setValor("No se encontró respuesta cerrada.");
            return dto;
        }
    }

    public PreguntaDTO procesarPreguntaConFormula(Pregunta p, Integer idConsulta) {
        PreguntaValorFijoDTO dto = new PreguntaValorFijoDTO();
        dto.setId(p.getId());
        dto.setTextoPregunta(p.getTextoPregunta());
        dto.setIdCategoria(p.getCategoria().getId());
        String formula = p.getPreguntaFormula().getFormula();
        String origen = p.getPreguntaFormula().getOrigen();
        FormulaProcessor formulaProcessor = new FormulaProcessor(this.respuestaAbiertaRepository,
                this.preguntaRepository, respuestaGraficoRepository, parteGraficoRepository);
        try {
            Double resultado = formulaProcessor.procesarFormula(formula, idConsulta, origen);
            if (resultado != null) {
                dto.setTipoRespuesta("Fijo");
                dto.setValor(resultado.toString());
                this.respuestaAbiertaService
                        .guardarRespuestaAbierta(new RespuestaAbiertaDTO(resultado.toString(), idConsulta, p.getId()));
            } else {
                dto.setTipoRespuesta("Error");
                dto.setValor("No se encontró respuesta para la fórmula.");
            }
            return dto;
        } catch (Exception e) {
            dto.setTipoRespuesta("Error");
            if(e.getMessage().equals("empty string")) {
                dto.setValor("Se borraron los datos de la pregunta, por favor volver a llenar.");
            }
            else if(e.getMessage().equals("Division by zero!")) {
                dto.setValor("No se puede dividir por cero, por favor revisar los valores.");
            }
            else {
                dto.setValor(e.getMessage());
            }
            dto.setValor(e.getMessage());
            return dto;
        }
    }

    private PreguntaConOpcionesDTO procesarPreguntaConOpciones(Pregunta p, Integer idConsulta) {
        PreguntaConOpcionesDTO dto = new PreguntaConOpcionesDTO(p.getId(), p.getTextoPregunta(), p.getTipoRespuesta(),
                p.getCategoria().getId());

        List<OpcionListadoDTO> opcionesDTO = new ArrayList<>();
        for (OpcionListado o : p.getPreguntaListado().getListado().getOpciones()) {
            OpcionListadoDTO opt = new OpcionListadoDTO(o.getId(), o.getTexto(), o.getTipoValor(), o.getValor());
            opcionesDTO.add(opt);
        }
        dto.setOpciones(opcionesDTO);
        Optional<RespuestaCerrada> respuestaCerrada = p.getRespuestasCerradas().stream()
                .filter(r -> r.getConsulta().getId().equals(idConsulta)).findFirst();
        if (respuestaCerrada.isPresent()) {
            dto.setOpcionSeleccionada(respuestaCerrada.get().getOpcion().getId());
        }

        return dto;
    }

    public PreguntaDTO procesarPreguntaConReferencia(Pregunta p, Integer idConsulta) {
        PreguntaValorFijoDTO dto = new PreguntaValorFijoDTO();
        dto.setId(p.getId());
        dto.setTextoPregunta(p.getTextoPregunta());
        dto.setIdCategoria(p.getCategoria().getId());

        String tipoReferencia = p.getPreguntaReferencia().getReferencia().getTipo();
        Pregunta preguntaReferenciaDatos = p.getPreguntaReferencia().getReferencia().getValorTomarPregunta();
        Optional<RespuestaAbierta> respuestaAbierta = preguntaReferenciaDatos
                .getRespuestasAbiertas().stream()
                .filter(r -> r.getPregunta().getId()
                        .equals(preguntaReferenciaDatos.getId()) &&
                        r.getConsulta().getId().equals(idConsulta))
                .findFirst();
        Optional<RespuestaCerrada> respuestaCerrada = preguntaReferenciaDatos
                .getRespuestasCerradas().stream().filter(r -> r.getPregunta().getId()
                        .equals(preguntaReferenciaDatos.getId()) &&
                        r.getConsulta().getId().equals(idConsulta))
                .findFirst();

        if (!respuestaAbierta.isPresent() && !respuestaCerrada.isPresent()) {
            dto.setTipoRespuesta("Error");
            dto.setValor("No se encontró respuesta. Por favor llenar primero la pregunta: " +
                    preguntaReferenciaDatos.getTextoPregunta() + " en " +
                    preguntaReferenciaDatos.getCategoria().getNombre());
            return dto;
        }

        if ("Condicional".equals(tipoReferencia)
                && "abierta".equals(p.getPreguntaReferencia().getReferencia().getOrigen())) {
            if (respuestaAbierta.isPresent()) {
                return procesarReferenciaCondicional(p, respuestaAbierta.get().getValor(), dto);
            }
        } else if ("Lista".equals(tipoReferencia)
                && "cerrada".equals(p.getPreguntaReferencia().getReferencia().getOrigen())) {
            if (respuestaCerrada.isPresent()) {
                return procesarReferenciaLista(p, respuestaCerrada.get().getOpcion().getValor(), dto);
            } else {
                dto.setTipoRespuesta("Error");
                dto.setValor("No se encontró respuesta cerrada.");
                return dto;
            }
        }

        dto.setTipoRespuesta("Error");
        dto.setValor("Tipo de referencia no soportado: " + tipoReferencia);
        return dto;
    }

    private PreguntaValorFijoDTO procesarReferenciaLista(Pregunta p, String respuesta, PreguntaValorFijoDTO dto) {
        for (Valor valor : p.getPreguntaReferencia().getReferencia().getValores()) {

            if ("Otro".equals(valor.getCondicional())
                    || Comparador.cumpleListado(respuesta, valor.getCondicional())) {
                dto.setTipoRespuesta("Fijo");
                dto.setValor(valor.getValorCondicional());
                return dto;
            }
        }
        dto.setTipoRespuesta("Error");
        dto.setValor(null);
        return dto;

    }

    private ParteGraficoDTO procesarGraficoReferenciaLista(ParteGrafico parteGrafico, String respuesta,
            ParteGraficoDTO dto) {
        for (Valor valor : parteGrafico.getGraficoReferencia().getReferencia().getValores()) {

            if ("Otro".equals(valor.getCondicional())
                    || Comparador.cumpleListado(respuesta, valor.getCondicional())) {
                dto.setTipoValor("Fijo");
                dto.setValor(valor.getValorCondicional());
                return dto;
            }
        }
        dto.setTipoValor("Error");
        dto.setValor(null);
        return dto;
    }

    private PreguntaValorFijoDTO procesarReferenciaCondicional(Pregunta p, String respuesta,
            PreguntaValorFijoDTO dto) {
        Double valorComparar = null;
        try {
            if (respuesta != null) {
                valorComparar = Double.parseDouble(respuesta);
            }
        } catch (NumberFormatException e) {
            dto.setTipoRespuesta("Error");
            dto.setValor("Valor inválido para comparación");
            return dto;
        }

        for (Valor valor : p.getPreguntaReferencia().getReferencia().getValores()) {
            String condicional = valor.getCondicional();

            if (condicional == null && valorComparar == null) {
                dto.setTipoRespuesta("Fijo");
                dto.setValor(null);
                break;
            } else if ("Otro".equals(condicional)
                    || Comparador.cumpleCondicion(condicional, valorComparar)) {
                dto.setTipoRespuesta("Fijo");
                dto.setValor(valor.getValorCondicional());
                break;
            }
        }

        if (dto.getValor() == null) {
            dto.setTipoRespuesta("Error");
            dto.setValor("Error en la comparación de valores condicionales");
        }

        return dto;
    }

    private PreguntaSinOpcionesDTO procesarPreguntaSinOpciones(Pregunta p, Integer idConsulta) {
        PreguntaSinOpcionesDTO preguntaSinOpcionesDTO = new PreguntaSinOpcionesDTO(p.getId(), p.getTextoPregunta(),
                p.getTipoRespuesta(),
                p.getCategoria().getId());
        Optional<RespuestaAbierta> respuestaAbierta = p.getRespuestasAbiertas().stream()
                .filter(r -> r.getConsulta().getId().equals(idConsulta)).findFirst();
        if (respuestaAbierta.isPresent()) {
            preguntaSinOpcionesDTO.setValorAlmacenado(respuestaAbierta.get().getValor());
        }
        return preguntaSinOpcionesDTO;
    }
}