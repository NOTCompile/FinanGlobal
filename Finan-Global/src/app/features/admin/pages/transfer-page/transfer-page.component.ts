import { Component, inject, OnInit, signal } from '@angular/core';
import { Tranferencia } from 'src/app/shared/interfaces/Transferencia-Interface';
import { ReporteService } from 'src/app/shared/services/Reporte.service';
import { TransferenciaService } from 'src/app/shared/services/Transferencia.service';
import { ModalTransferenciaAdministrador } from '../../services/mmodalTransferencia.service';

@Component({
  selector: 'app-transfer-page',
  imports: [],
  templateUrl: './transfer-page.component.html',
  styleUrl: './transfer-page.component.css',
})
export default class TransferPageComponent implements OnInit {
  // Servicios
  private transferenciaService = inject(TransferenciaService);
  private modalService = inject(ModalTransferenciaAdministrador);
  private reporteService = inject(ReporteService); // PDF

  // Estado
  transferencia = this.transferenciaService.transferencias;
  modo = signal<'agregar' | 'editar'>('agregar');
  transferenciaSeleccionada = signal<Partial<Tranferencia> | null>(null);

  ngOnInit(): void {
    this.cargarTipoDocumentos();
  }

  /* Cargar Datos */
  // Cargar Tipo Documentos
  cargarTipoDocumentos(): void {
    this.transferenciaService.getAll().subscribe({
      next: () => {
        console.log('Tipo Documentos cargado');
      },
      error: (err) => console.error('Error al cargar tipo documentos:', err),
    });
  }

  /* Funciones */
  abrirModalAgregar(): void {
    this.modo.set('agregar');
    this.transferenciaSeleccionada.set({
      dni_ruc: '',
      correo: '',
      contrasena: '',
      nombre: '',
      apellidos: '',
      direccion: '',
      sexo: 'Seleccione...',
      telefono: '',
      rol_usuario: 0,
    });
    this.modalService.open();
    console.log('Modal Abierto');
  }

  /* PDF */
  obtenerPDFGeneral(nombre: string) {
    this.reporteService.descargarReporteGeneral(nombre).subscribe({
      next: (data: Blob) => {
        const file = new Blob([data], { type: 'application/pdf' });
        const fileURL = URL.createObjectURL(file);
        window.open(fileURL, '_blank');
      },
      error: (err) => console.error('Error al mostrar PDF:', err),
    });
  }
}
