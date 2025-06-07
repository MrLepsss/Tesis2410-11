package com.intellectus.backend.servicios;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellectus.backend.auxiliar.HeaderFooter;
import com.intellectus.backend.dto.ReporteDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import com.itextpdf.text.Image;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;


@Service
public class PdfGeneratorService {

    private final ReporteService reporteService;
    private final ArchivoConsultaService archivoConsultaService;
    private final ObjectMapper mapper;
    private final ClassPathResource fontRes = new ClassPathResource("Candara.ttf");
    private final Font titleFont;
    private final Font subtitleFont;
    private final Font textFont;
    private final Font textBold;
    private final Font footerFont;

    public PdfGeneratorService(ReporteService reporteService,
            ObjectMapper mapper,
            ArchivoConsultaService archivoConsultaService) throws Exception {
        this.reporteService = reporteService;
        this.mapper = mapper;
        this.archivoConsultaService = archivoConsultaService;

        // Registrar fuente
        FontFactory.register(fontRes.getURL().toExternalForm(), "Candara");
        this.titleFont = FontFactory.getFont("Candara", BaseFont.WINANSI, BaseFont.EMBEDDED, 14, Font.BOLD);
        this.subtitleFont = FontFactory.getFont("Candara", BaseFont.WINANSI, BaseFont.EMBEDDED, 12, Font.BOLD);
        this.textFont = FontFactory.getFont("Candara", BaseFont.WINANSI, BaseFont.EMBEDDED, 11, Font.NORMAL);
        this.textBold = FontFactory.getFont("Candara", BaseFont.WINANSI, BaseFont.EMBEDDED, 11, Font.BOLD);
        this.footerFont = FontFactory.getFont("Candara", BaseFont.WINANSI, BaseFont.EMBEDDED, 9, Font.NORMAL,
                BaseColor.DARK_GRAY);
    }

    public ResponseEntity<ByteArrayResource> generarInforme(Integer consultaId) throws Exception {
        // Obtener datos JSON
        ReporteDTO reporteDto = reporteService.generarReporte(consultaId);
        if (reporteDto == null) {
            throw new IllegalArgumentException("No se encontró el reporte para la consulta ID: " + consultaId);
        }
        byte[] datosJsonBytes = mapper.writeValueAsBytes(reporteDto);
        String json = new String(datosJsonBytes, StandardCharsets.UTF_8);
        JsonObject root = JsonParser.parseString(json).getAsJsonObject();
        JsonArray areas = root.has("areas") && root.get("areas").isJsonArray()
                ? root.getAsJsonArray("areas")
                : new JsonArray();

        // Extraer remitente (para cierre)
        String remitente = "";
        for (JsonElement ae : areas) {
            JsonObject area = ae.getAsJsonObject();
            if ("General".equalsIgnoreCase(getSafe(area, "nombre"))) {
                JsonArray cats = area.getAsJsonArray("categorias");
                if (cats.size() > 0) {
                    JsonArray preguntas = cats.get(0).getAsJsonObject().getAsJsonArray("preguntas");
                    for (JsonElement pe : preguntas) {
                        JsonObject p = pe.getAsJsonObject();
                        if ("Remite".equalsIgnoreCase(getSafe(p, "pregunta"))) {
                            remitente = getSafe(p, "respuesta");
                            break;
                        }
                    }
                }
                break;
            }
        }

        // Crear documento PDF
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4, 50, 50, 120, 90);
        PdfWriter writer = PdfWriter.getInstance(doc, baos);
        writer.setPageEvent(new HeaderFooter(footerFont));
        doc.open();

        // Títulos
        Paragraph p1 = new Paragraph("Clínica de Memoria", titleFont);
        p1.setAlignment(Element.ALIGN_CENTER);
        doc.add(p1);
        Paragraph p2 = new Paragraph("Informe de Evaluación Interdisciplinaria", subtitleFont);
        p2.setAlignment(Element.ALIGN_CENTER);
        doc.add(p2);
        doc.add(Chunk.NEWLINE);

        // Recorrer áreas
        for (JsonElement ae : areas) {
            JsonObject area = ae.getAsJsonObject();
            String areaName = getSafe(area, "nombre");

            // Título sección
            Paragraph section = new Paragraph(areaName + ":", subtitleFont);
            section.setAlignment(Element.ALIGN_JUSTIFIED);
            section.setLeading(18);
            doc.add(section);
            doc.add(Chunk.NEWLINE);

            if ("General".equalsIgnoreCase(areaName)) {
                // ----- DOS COLUMNAS para la primera categoría (Identificación) -----
                JsonArray cats = area.getAsJsonArray("categorias");
                if (cats.size() > 0) {
                    JsonObject identificacion = cats.get(0).getAsJsonObject();
                    JsonArray infoList = identificacion.getAsJsonArray("preguntas");

                    // Construir tabla de identificación
                    PdfPTable tablaId = new PdfPTable(2);
                    tablaId.setWidthPercentage(100);
                    tablaId.setSpacingBefore(5f);
                    tablaId.setWidths(new float[] { 1f, 1f });

                    int total = infoList.size();
                    int mitad = (total + 1) / 2;
                    for (int i = 0; i < mitad; i++) {
                        // Celda izquierda
                        JsonObject itemL = infoList.get(i).getAsJsonObject();
                        Phrase phL = new Phrase();
                        phL.add(new Chunk(getSafe(itemL, "pregunta") + ": ", textBold));
                        phL.add(new Chunk(getSafe(itemL, "respuesta"), textFont));
                        PdfPCell cellL = new PdfPCell(phL);
                        cellL.setBorder(Rectangle.NO_BORDER);
                        tablaId.addCell(cellL);

                        // Celda derecha
                        int j = i + mitad;
                        if (j < total) {
                            JsonObject itemR = infoList.get(j).getAsJsonObject();
                            Phrase phR = new Phrase();
                            phR.add(new Chunk(getSafe(itemR, "pregunta") + ": ", textBold));
                            phR.add(new Chunk(getSafe(itemR, "respuesta"), textFont));
                            PdfPCell cellR = new PdfPCell(phR);
                            cellR.setBorder(Rectangle.NO_BORDER);
                            tablaId.addCell(cellR);
                        } else {
                            PdfPCell empty = new PdfPCell(new Phrase(" "));
                            empty.setBorder(Rectangle.NO_BORDER);
                            tablaId.addCell(empty);
                        }
                    }
                    doc.add(tablaId);
                    doc.add(Chunk.NEWLINE);
                }

                // ----- Resto de categorías de 'General' en una columna -----
                for (int k = 1; k < cats.size(); k++) {
                    JsonObject cat = cats.get(k).getAsJsonObject();
                    Paragraph catTitle = new Paragraph(getSafe(cat, "pregunta") + ":", textBold);
                    catTitle.setAlignment(Element.ALIGN_JUSTIFIED);
                    catTitle.setLeading(18);
                    doc.add(catTitle);

                    if (cat.has("preguntas")) {
                        for (JsonElement pe : cat.getAsJsonArray("preguntas")) {
                            JsonObject sub = pe.getAsJsonObject();
                            Paragraph subPara = new Paragraph();
                            subPara.setAlignment(Element.ALIGN_JUSTIFIED);
                            subPara.setLeading(18);
                            subPara.add(new Chunk("• " + getSafe(sub, "pregunta") + ": ", textBold));
                            subPara.add(new Chunk(getSafe(sub, "respuesta"), textFont));
                            doc.add(subPara);
                        }
                    } else if (cat.has("respuesta")) {
                        Paragraph aPara = new Paragraph(getSafe(cat, "respuesta"), textFont);
                        aPara.setAlignment(Element.ALIGN_JUSTIFIED);
                        aPara.setLeading(18);
                        doc.add(aPara);
                    }
                    doc.add(Chunk.NEWLINE);
                }

            } else {
                // ----- UNA COLUMNA para demás áreas -----
                JsonArray cats = area.has("categorias") ? area.getAsJsonArray("categorias") : new JsonArray();
                for (JsonElement ce : cats) {
                    JsonObject cat = ce.getAsJsonObject();
                    Paragraph qPara = new Paragraph(getSafe(cat, "pregunta") + ":", textBold);
                    qPara.setAlignment(Element.ALIGN_JUSTIFIED);
                    qPara.setLeading(18);
                    doc.add(qPara);

                    if (cat.has("respuesta")) {
                        Paragraph aPara = new Paragraph(getSafe(cat, "respuesta"), textFont);
                        aPara.setAlignment(Element.ALIGN_JUSTIFIED);
                        aPara.setLeading(18);
                        doc.add(aPara);
                    } else if (cat.has("preguntas")) {
                        for (JsonElement pe : cat.getAsJsonArray("preguntas")) {
                            JsonObject sub = pe.getAsJsonObject();
                            Paragraph subPara = new Paragraph();
                            subPara.setAlignment(Element.ALIGN_JUSTIFIED);
                            subPara.setLeading(18);
                            subPara.add(new Chunk("• " + getSafe(sub, "pregunta") + ": ", textBold));
                            subPara.add(new Chunk(getSafe(sub, "respuesta"), textFont));
                            doc.add(subPara);
                        }
                    }
                    doc.add(Chunk.NEWLINE);
                }
            }

            // --- INTEGRAR GRÁFICOS EN NEUROPSICOLOGÍA ---
            if ("Neuropsicología".equalsIgnoreCase(areaName)) {
                JsonArray cats = area.has("categorias") ? area.getAsJsonArray("categorias") : new JsonArray();
                for (JsonElement ce : cats) {
                    JsonObject cat = ce.getAsJsonObject();
                    String pregunta = getSafe(cat, "pregunta");
                    String tipo = getSafe(cat, "tipo");
                    if (tipo.equalsIgnoreCase("Columnas") || tipo.equalsIgnoreCase("Linea con Marcadores")) {
                        // 1. Crear dataset desde JSON
                        DefaultCategoryDataset dataset = buildDatasetFromJson(cat);

                        // 2. Crear gráfico
                        JFreeChart chart;
                        if (tipo.equalsIgnoreCase("Columnas")) {
                            chart = ChartFactory.createBarChart(pregunta, "Categoría", "Valor", dataset);
                        } else {
                            chart = ChartFactory.createLineChart(pregunta, "", "", dataset);
                            CategoryPlot plot = chart.getCategoryPlot();
                            CategoryAxis axis = plot.getDomainAxis();
                            axis.setCategoryLabelPositions(
                                    CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 4.0));
                            LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
                            for (int i = 0; i < dataset.getRowCount(); i++) {
                                renderer.setSeriesShapesVisible(i, true);
                            }
                        }

                        // 3. Guardar imagen temporal
                        java.io.File tempImg = java.io.File.createTempFile("chart", ".png");
                        ChartUtils.saveChartAsPNG(tempImg, chart, 500, 300);

                        // 4. Insertar imagen en PDF
                        Image img = Image.getInstance(tempImg.getAbsolutePath());
                        img.scaleToFit(doc.getPageSize().getWidth() - doc.leftMargin() - doc.rightMargin(), 300);
                        img.setAlignment(Image.ALIGN_CENTER);
                        doc.add(img);

                        tempImg.delete(); // Limpia el archivo temporal
                    }
                }
            }
        }

        // Cierre
        if (!remitente.isEmpty()) {
            Paragraph cierre = new Paragraph();
            cierre.setAlignment(Element.ALIGN_JUSTIFIED);
            cierre.setLeading(18);
            cierre.add(new Chunk("Muchas gracias ", textFont));
            cierre.add(new Chunk(remitente, textBold));
            cierre.add(new Chunk(
                    " por su remisión, confiamos que nuestra evaluación sea de su utilidad. " +
                            "Por favor si precisa mayor información o alguna sugerencia contáctenos.",
                    textFont));
            doc.add(cierre);
        }

        doc.close();

        // Guardar y devolver PDF
        byte[] pdfBytes = baos.toByteArray();
        MultipartFile pdfMultipart = new MockMultipartFile(
                "archivo",
                "informe.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                pdfBytes);
        archivoConsultaService.guardarArchivo(consultaId, pdfMultipart, true);

        ByteArrayResource resource = new ByteArrayResource(pdfBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("informe.pdf").build());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(pdfBytes.length)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    private static String getSafe(JsonObject obj, String key) {
        return obj.has(key) && !obj.get(key).isJsonNull() ? obj.get(key).getAsString() : "";
    }

    // --- MÉTODO AUXILIAR PARA CREAR DATASET DESDE JSON ---
    private DefaultCategoryDataset buildDatasetFromJson(JsonObject cat) {
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        if (cat.has("secciones")) {
            for (JsonElement se : cat.getAsJsonArray("secciones")) {
                JsonObject seccion = se.getAsJsonObject();
                String nombreSeccion = getSafe(seccion, "seccion");
                if (seccion.has("partes")) {
                    for (JsonElement parte : seccion.getAsJsonArray("partes")) {
                        JsonObject p = parte.getAsJsonObject();
                        String pregunta = getSafe(p, "pregunta");
                        String respuesta = getSafe(p, "respuesta");
                        try {
                            double valor = Double.parseDouble(respuesta.replace(",", "."));
                            ds.addValue(valor, nombreSeccion, pregunta);
                        } catch (NumberFormatException e) {
                            // Si no es numérico, ignora
                        }
                    }
                }
            }
        }
        return ds;
    }
}
