package banco.controllers;

import banco.models.Documento;
import banco.models.TipoDocumento;
import banco.services.DocumentoService;
import banco.services.TipoDocumentoService; // Necesario para buscar la relación
import banco.dto.DocumentoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/documentos")
public class DocumentoController {

    private final DocumentoService documentoService;
    private final TipoDocumentoService tipoDocumentoService;

    // Inyección por constructor
    public DocumentoController(DocumentoService documentoService, TipoDocumentoService tipoDocumentoService) {
        this.documentoService = documentoService;
        this.tipoDocumentoService = tipoDocumentoService;
    }

    // 1. OBTENER TODOS
    @GetMapping
    public List<Documento> getAllDocumentos() {
        return documentoService.findAll();
    }

    // 2. OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Documento> getDocumentoById(@PathVariable Integer id) {
        return documentoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. CREAR NUEVO DOCUMENTO (POST /api/documentos)
    @PostMapping
    public ResponseEntity<Documento> createDocumento(@RequestBody DocumentoDTO documentoDto) {

        // 1. Buscamos el objeto TipoDocumento completo
        TipoDocumento tipoDocumento = tipoDocumentoService.findById(documentoDto.getIdTipoDocumento())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tipo de Documento con ID " + documentoDto.getIdTipoDocumento() + " no encontrado."
                ));

        // 2. Mapeamos DTO a Entidad
        Documento nuevoDocumento = new Documento();
        nuevoDocumento.setNombre(documentoDto.getNombre());
        nuevoDocumento.setFecha(documentoDto.getFecha());
        nuevoDocumento.setRutaArchivo(documentoDto.getRutaArchivo());

        // 3. Asignamos la relación
        nuevoDocumento.setTipoDocumento(tipoDocumento);

        // 4. Guardamos
        Documento guardado = documentoService.save(nuevoDocumento);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // 4. ACTUALIZAR DOCUMENTO (PUT /api/documentos/{id})
    @PutMapping("/{id}")
    public ResponseEntity<Documento> updateDocumento(@PathVariable Integer id, @RequestBody Documento documento) {
        if (!id.equals(documento.getId())) {
            return ResponseEntity.badRequest().build();
        }

        // Si se intenta actualizar la relación, buscamos el TipoDocumento
        if (documento.getTipoDocumento() != null && documento.getTipoDocumento().getId() != null) {
            Integer idTipo = documento.getTipoDocumento().getId();
            TipoDocumento tipoDocumento = tipoDocumentoService.findById(idTipo)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Tipo de Documento con ID " + idTipo + " no encontrado durante la actualización."
                    ));
            documento.setTipoDocumento(tipoDocumento);
        }

        try {
            Documento actualizado = documentoService.update(documento);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. ELIMINAR DOCUMENTO
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumento(@PathVariable Integer id) {
        documentoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}