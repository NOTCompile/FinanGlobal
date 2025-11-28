import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
import { TransferenciaService } from 'src/app/shared/services/Transferencia.service';

@Component({
  selector: 'app-transfer-page',
  imports: [],
  templateUrl: './transfer-page.component.html',
  styleUrl: './transfer-page.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export default class TransferPageComponent implements OnInit {
  // Servicios
  private transferenciaService = inject(TransferenciaService);

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
}
