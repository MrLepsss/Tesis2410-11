package com.intellectus.backend.controllers;
import com.intellectus.backend.servicios.PdfGeneratorService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReporteController {
    private final PdfGeneratorService pdfService;

    public ReporteController(PdfGeneratorService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping(value = "/informe/{consultaId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<ByteArrayResource> generarInforme(@PathVariable Integer consultaId) throws Exception {
        return pdfService.generarInforme(consultaId);
    }
}
