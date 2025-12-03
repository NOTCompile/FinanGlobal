import { Component, inject, OnInit } from '@angular/core';
import { ReporteService } from 'src/app/shared/services/Reporte.service';
import { TransferenciaService } from 'src/app/shared/services/Transferencia.service';

@Component({
  selector: 'app-transfer-page',
  imports: [],
  templateUrl: './transfer-page.component.html',
  styleUrl: './transfer-page.component.css',
})
export default class TransferPageComponent implements OnInit {
  // Servicios
  private transferenciaService = inject(TransferenciaService);
  private reporteService = inject(ReporteService); // PDF

  // Estado
  transferencia = this.transferenciaService.transferencias;

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
