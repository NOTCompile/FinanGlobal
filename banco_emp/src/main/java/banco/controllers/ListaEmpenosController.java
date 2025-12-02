package banco.controllers;

import banco.models.ListaEmpenos;
import banco.services.ListaEmpenosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lista-empenos")
public class ListaEmpenosController {

    private final ListaEmpenosService listaEmpenosService;

    public ListaEmpenosController(ListaEmpenosService listaEmpenosService) {
        this.listaEmpenosService = listaEmpenosService;
    }

    @GetMapping
    public ResponseEntity<List<ListaEmpenos>> getAll() {
        List<ListaEmpenos> listaEmpenos = listaEmpenosService.findAll();
        return ResponseEntity.ok(listaEmpenos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaEmpenos> getById(@PathVariable Integer id) {
        return listaEmpenosService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ListaEmpenos>> getByUsuarioId(@PathVariable Integer idUsuario) {
        List<ListaEmpenos> listaEmpenos = listaEmpenosService.findByUsuarioId(idUsuario);
        return ResponseEntity.ok(listaEmpenos);
    }

    @GetMapping("/empeno/{idEmpeno}")
    public ResponseEntity<List<ListaEmpenos>> getByEmpenoId(@PathVariable Integer idEmpeno) {
        List<ListaEmpenos> listaEmpenos = listaEmpenosService.findByEmpenoId(idEmpeno);
        return ResponseEntity.ok(listaEmpenos);
    }

    @PostMapping
    public ResponseEntity<ListaEmpenos> create(@RequestBody ListaEmpenos listaEmpenos) {
        ListaEmpenos savedListaEmpenos = listaEmpenosService.save(listaEmpenos);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedListaEmpenos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListaEmpenos> update(@PathVariable Integer id, @RequestBody ListaEmpenos listaEmpenos) {
        listaEmpenos.setId(id);
        ListaEmpenos updatedListaEmpenos = listaEmpenosService.update(listaEmpenos);
        return ResponseEntity.ok(updatedListaEmpenos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        listaEmpenosService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

