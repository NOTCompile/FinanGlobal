package banco.controllers;

import banco.models.ListaDocumentos;
import banco.models.Usuario;
import banco.models.Documento;
import banco.services.ListaDocumentosService;
import banco.services.UsuariosService;    // Asumiendo tu servicio de Usuario
import banco.services.DocumentoService;  // Asumiendo un servicio para Documento
import banco.dto.ListaDocumentosDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/lista-documentos")
public class ListaDocumentosController {

    private final ListaDocumentosService listaDocumentosService;
    private final UsuariosService usuariosService;
    private final DocumentoService documentoService;

    public ListaDocumentosController(
            ListaDocumentosService listaDocumentosService,
            UsuariosService usuariosService,
            DocumentoService documentoService) {
        this.listaDocumentosService = listaDocumentosService;
        this.usuariosService = usuariosService;
        this.documentoService = documentoService;
    }

    // 1. OBTENER TODAS LAS RELACIONES
    @GetMapping
    public List<ListaDocumentos> getAllRelaciones() {
        return listaDocumentosService.findAll();
    }

    // 2. OBTENER DOCUMENTOS POR USUARIO
    @GetMapping("/usuario/{idUsuario}")
    public List<ListaDocumentos> getDocumentosByUsuario(@PathVariable Integer idUsuario) {
        return listaDocumentosService.findByUsuarioId(idUsuario);
    }

    // 3. CREAR NUEVA RELACIÓN (POST /api/lista-documentos)
    @PostMapping
    public ResponseEntity<ListaDocumentos> createRelacion(@RequestBody ListaDocumentosDTO dto) {

        // 1. Buscar y validar Usuario
        Usuario usuario = usuariosService.findById(dto.getIdUsuario())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario con ID " + dto.getIdUsuario() + " no encontrado."
                ));

        // 2. Buscar y validar Documento
        Documento documento = documentoService.findById(dto.getIdDocumento())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Documento con ID " + dto.getIdDocumento() + " no encontrado."
                ));

        // 3. Mapear DTO a Entidad
        ListaDocumentos nuevaRelacion = new ListaDocumentos();
        nuevaRelacion.setUsuario(usuario);
        nuevaRelacion.setDocumento(documento);

        // 4. Guardar
        ListaDocumentos guardada = listaDocumentosService.save(nuevaRelacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    // 4. ELIMINAR RELACIÓN
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRelacion(@PathVariable Integer id) {
        listaDocumentosService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}