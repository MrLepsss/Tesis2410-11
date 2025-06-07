package com.intellectus.backend.controllers;

import com.intellectus.backend.dto.FilesDTO;
import com.intellectus.backend.dto.ReporteDTO;
import com.intellectus.backend.dto.TranscriptionResponseDto;
import com.intellectus.backend.servicios.ArchivoConsultaService;
import com.intellectus.backend.servicios.ReporteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/archivos")
public class ArchivoConsultaController {

    private final ArchivoConsultaService archivoConsultaService;
    private final ReporteService reporteService;

    @Autowired
    public ArchivoConsultaController(ArchivoConsultaService archivoConsultaService,
                                     ReporteService reporteService) {
        this.archivoConsultaService = archivoConsultaService;
        this.reporteService = reporteService;
    }

    @GetMapping("/paciente/{id}")
    public ResponseEntity<List<FilesDTO>> obtenerArchivosPorPacienteId(@PathVariable Long id) {
        return ResponseEntity.ok(archivoConsultaService.obtenerArchivosPorPacienteId(id));
    }

    @GetMapping("/paciente/cedula/{cedula}")
    public ResponseEntity<List<FilesDTO>> obtenerArchivosPorPacienteCedula(@PathVariable String cedula) {
        return ResponseEntity.ok(archivoConsultaService.obtenerArchivosPorPacienteCedula(cedula));
    }

    @GetMapping("/descargar/{archivoId}")
    public ResponseEntity<Resource> descargarArchivo(@PathVariable Integer archivoId) {
        return archivoConsultaService.obtenerArchivoPorRuta(archivoId);
    }

    @PostMapping("/subir")
    public ResponseEntity<String> subirArchivo(
            @RequestParam("consultaId") Integer consultaId,
            @RequestParam("archivo") MultipartFile archivo) {
        return archivoConsultaService.guardarArchivo(consultaId, archivo);
    }

    @PostMapping("/transcripcion")
    public ResponseEntity<String> guardarTranscripcion(@RequestBody TranscriptionResponseDto dto) throws IOException {
        return archivoConsultaService.crearArchivoTxt(dto);
    }

    @PostMapping("/crearReporte/{consultaId}")
    public ReporteDTO crearReporteConsulta(@PathVariable Integer consultaId)
    {
        return reporteService.generarReporte(consultaId);
    }
}

