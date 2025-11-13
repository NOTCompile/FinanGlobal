package banco.controllers;

import banco.services.ServicioReporteJasper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
public class ControladorReporte {

    private final ServicioReporteJasper jasperService;

    public ControladorReporte (ServicioReporteJasper jasperService) {
        this.jasperService = jasperService;
    }

    // Descargar reporte PDF
    @GetMapping("/descargar")
    public ResponseEntity<byte[]> downloadReport(@RequestParam String nombre) throws Exception {
        Map<String, Object> params = new HashMap<>();
        byte[] pdf = jasperService.generatePdf(nombre + ".jrxml", params);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + nombre + ".pdf");
        return ResponseEntity.ok().headers(headers).body(pdf);
    }
}