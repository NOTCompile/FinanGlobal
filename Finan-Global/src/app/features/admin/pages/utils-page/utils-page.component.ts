import { Component, inject, OnInit } from '@angular/core';
import { TipoDocumentoService } from 'src/app/shared/services/TipoDocumento.service';
import { TipoProductoService } from 'src/app/shared/services/TipoProducto.service';
import { TipoTransferenciaService } from 'src/app/shared/services/TipoTransferencia.service';

@Component({
  selector: 'app-utils-page',
  imports: [],
  templateUrl: './utils-page.component.html',
  styleUrl: './utils-page.component.css',
})
export default class UtilsAdmin implements OnInit {
  // Servicios
  private tipoDocumentoService = inject(TipoDocumentoService);
  private tipoProductoService = inject(TipoProductoService);
  private tipoTranferenciaService = inject(TipoTransferenciaService);

  // Estado
  tipoDocumento = this.tipoDocumentoService.tipoDocumentos;
  tipoProducto = this.tipoProductoService.tipoProductos;
  tipoTransferencia = this.tipoTranferenciaService.tipoTransferencias;

  ngOnInit(): void {
    this.cargarTipoDocumentos();
  }

  /* Cargar Datos */
  // Cargar Tipo Documentos
  cargarTipoDocumentos(): void {
    this.tipoDocumentoService.getAll().subscribe({
      next: () => {
        console.log('Tipo Documentos cargado');
      },
      error: (err) => console.error('Error al cargar tipo documentos:', err),
    });
  }

  // Cargar Tipo Productos
  cargarTipoProductos(): void {
    this.tipoProductoService.getAll().subscribe({
      next: () => {
        console.log('Tipo Documentos cargado');
      },
      error: (err) => console.error('Error al cargar tipo documentos:', err),
    });
  }

  // Cargar Tipo Transferencias
  cargarTipoTransferencias(): void {
    this.tipoTranferenciaService.getAll().subscribe({
      next: () => {
        console.log('Tipo Documentos cargado');
      },
      error: (err) => console.error('Error al cargar tipo documentos:', err),
    });
  }
}
