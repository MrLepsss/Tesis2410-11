package com.intellectus.backend.servicios;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.intellectus.backend.dto.FilesDTO;
import com.intellectus.backend.dto.TranscriptionResponseDto;
import com.intellectus.backend.entities.ArchivoConsulta;
import com.intellectus.backend.entities.Consulta;
import com.intellectus.backend.repositorios.ArchivoConsultaRepository;
import com.intellectus.backend.repositorios.ConsultaRepository;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.io.File;

@Service
public class ArchivoConsultaService {

    @Value("${app.archivos.ruta-base}")
    private String rutaBase;

    private final ArchivoConsultaRepository archivoRepository;
    private final ConsultaRepository consultaRepository;

    public ArchivoConsultaService(ArchivoConsultaRepository archivoRepository, ConsultaRepository consultaRepository) {
        this.archivoRepository = archivoRepository;
        this.consultaRepository = consultaRepository;
    }

    public ResponseEntity<String> crearArchivoTxt(TranscriptionResponseDto dto) throws IOException {
        Consulta consulta = consultaRepository.findById(dto.getId()).orElse(null);
        if (consulta == null) {
            return ResponseEntity.status(404).body("Consulta no encontrada con ID: " + dto.getId());
        }
        Optional<ArchivoConsulta> archivo = archivoRepository.findByRutaArchivo(dto.getAudioPath());
        if (!archivo.isPresent()) {
            ArchivoConsulta nuevoArchivo = new ArchivoConsulta();
            nuevoArchivo.setConsulta(consulta);
            nuevoArchivo.setTipoArchivo("Audio");
            nuevoArchivo.setRutaArchivo(dto.getAudioPath());
            archivoRepository.save(nuevoArchivo);
        }
        String nombreArchivo = "Audioconsulta_" + dto.getId() + "_" + System.currentTimeMillis() + ".txt";
        String rutaCompleta = Paths.get(rutaBase, nombreArchivo).toString();
        Files.write(Paths.get(rutaCompleta), dto.getTranscription().getBytes());
        ArchivoConsulta nuevoArchivo = new ArchivoConsulta();
        nuevoArchivo.setRutaArchivo(nombreArchivo);
        nuevoArchivo.setConsulta(consulta);
        nuevoArchivo.setTipoArchivo("Transcripcion");
        archivoRepository.save(nuevoArchivo);
        return ResponseEntity.ok("Archivo creado exitosamente en la ruta: " + rutaCompleta);
    }

    public ResponseEntity<Resource> obtenerArchivoPorRuta(Integer archivoId) {
        ArchivoConsulta archivoConsulta = archivoRepository.findById(archivoId).orElse(null);
        if (archivoConsulta == null) {
            return ResponseEntity.notFound().build();
        }
        String ruta = archivoConsulta.getRutaArchivo();
        String rutaCompleta = Paths.get(rutaBase, ruta).toString();
        File archivo = new File(rutaCompleta);
        if (!archivo.exists()) {
            return ResponseEntity.notFound().build();
        }

        String contentType;
        if (ruta.endsWith(".pdf")) {
            contentType = MediaType.APPLICATION_PDF_VALUE;
        } else if (ruta.endsWith(".mp4")) {
            contentType = "video/mp4";
        } else if (ruta.endsWith(".jpg") || ruta.endsWith(".jpeg")) {
            contentType = MediaType.IMAGE_JPEG_VALUE;
        } else if (ruta.endsWith(".png")) {
            contentType = MediaType.IMAGE_PNG_VALUE;
        } else if (ruta.endsWith(".txt")) {
            contentType = MediaType.TEXT_PLAIN_VALUE;
        } else {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        Resource recurso = new FileSystemResource(archivo);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + archivo.getName() + "\"")
                .contentType(MediaType.parseMediaType(contentType))
                .body(recurso);
    }

    public List<FilesDTO> obtenerArchivosPorPacienteId(Long pacienteId) {
        return archivoRepository.findAllByConsulta_Paciente_Id(pacienteId)
                .stream()
                .map(file -> new FilesDTO(
                        file.getId(),
                        file.getRutaArchivo().substring(file.getRutaArchivo().lastIndexOf("/") + 1),
                        file.getTipoArchivo()))
                .toList();
    }

    public List<FilesDTO> obtenerArchivosPorPacienteCedula(String cedula) {
        return archivoRepository.findAllByConsulta_Paciente_Cedula(cedula)
                .stream()
                .map(file -> new FilesDTO(
                        file.getId(),
                        file.getRutaArchivo().substring(file.getRutaArchivo().lastIndexOf("/") + 1),
                        file.getTipoArchivo()))
                .toList();
    }

    public ResponseEntity<String> guardarArchivo(Integer consultaId, MultipartFile archivo, boolean report) {
        try {
            Consulta consulta = consultaRepository.findById(consultaId).orElse(null);
            if (consulta == null) {
                return ResponseEntity.status(404).body("Consulta no encontrada con ID: " + consultaId);
            }

            String nombreOriginal = archivo.getOriginalFilename();
            if (nombreOriginal == null) {
                return ResponseEntity.badRequest().body("Archivo sin nombre válido.");
            }

            String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf('.') + 1).toLowerCase();
            String tipo;
            if (extension.equals("pdf")) {
                tipo = "PDF";
            } else if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png")) {
                tipo = "Imagen";
            } else {
                return ResponseEntity.badRequest().body("Tipo de archivo no soportado: " + extension);
            }
            String nuevoNombre;
            if( tipo.equals("PDF") && report) {
                nuevoNombre = "consulta_" +consulta.getFechaEvaluacion().toString() + "_" + consulta.getPaciente().getNombre()+ ".pdf";
            } else {
                nuevoNombre = "Anexo_" + consulta.getPaciente().getNombre() + "_" + System.currentTimeMillis() + "." + extension;
            }
            String rutaDestino = Paths.get(rutaBase, nuevoNombre).toString();
            Files.write(Paths.get(rutaDestino), archivo.getBytes());
            Optional<ArchivoConsulta> existente = archivoRepository
                .findByRutaArchivo(rutaDestino);

                        if (existente != null) {
                            ArchivoConsulta nuevoArchivo = new ArchivoConsulta();
                            nuevoArchivo.setConsulta(consulta);
                            nuevoArchivo.setTipoArchivo(tipo);
                            nuevoArchivo.setRutaArchivo(nuevoNombre);
                            archivoRepository.save(nuevoArchivo);
                        }

            return ResponseEntity.ok("Archivo guardado correctamente: " + nuevoNombre);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al guardar archivo: " + e.getMessage());
        }
    }
}