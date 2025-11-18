package banco.services;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.Map;

@Service
public class ServicioReporteJasper {

    private final DataSource dataSource;
    private final ResourceLoader resourceLoader;

    public ServicioReporteJasper(DataSource dataSource, ResourceLoader resourceLoader) {
        this.dataSource = dataSource;
        this.resourceLoader = resourceLoader;
    }

    // Compilar JRXML desde resources/reports/
    public JasperReport compileReport(String jrxmlFileName) throws JRException {
        try {
            Resource res = resourceLoader.getResource("classpath:reports/" + jrxmlFileName);
            try (InputStream is = res.getInputStream()) {
                return JasperCompileManager.compileReport(is);
            }
        } catch (Exception ex) {
            throw new JRException("Error compilando JRXML: " + jrxmlFileName, ex);
        }
    }

    // Generar PDF usando conexión JDBC
    public byte[] generatePdf(String jrxmlFileName, Map<String, Object> params) throws Exception {
        JasperReport jasperReport = compileReport(jrxmlFileName);
        try (Connection conn = dataSource.getConnection()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
    }
}
