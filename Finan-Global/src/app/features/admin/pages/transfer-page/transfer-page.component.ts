import { Component, inject, OnInit, signal } from '@angular/core';
import { Tranferencia } from 'src/app/shared/interfaces/Transferencia-Interface';
import { ReporteService } from 'src/app/shared/services/Reporte.service';
import { TransferenciaService } from 'src/app/shared/services/Transferencia.service';
import { ModalTransferenciaAdministrador } from '../../services/mmodalTransferencia.service';
import { TranferenciaDTO } from 'src/app/shared/interfaces/DTO/TransferenciaDTO-Interface';
import { AddTransferAdmin } from '../../components/modals/add-transfer-admin/add-transfer-admin';

@Component({
  selector: 'app-transfer-page',
  imports: [AddTransferAdmin],
  providers: [ModalTransferenciaAdministrador],
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
  transferenciaSeleccionada = signal<Partial<TranferenciaDTO> | null>(null);

  ngOnInit(): void {
    this.cargarTransferenecias();
  }

  /* Cargar Datos */
  // Cargar Tipo Documentos
  cargarTransferenecias(): void {
    this.transferenciaService.getAll().subscribe({
      next: () => {
        console.log('Tipo Documentos cargado');
      },
      error: (err) => console.error('Error al cargar tipo documentos:', err),
    });
  }

  // Ordenar
  private ordenarCuentas(): void {
    const ordenadas = [...this.transferenciaService.transferencias()].sort((a, b) => a.id - b.id);
    this.transferenciaService.transferencias.set(ordenadas);
  }

  /* Funciones */
  abrirModalAgregar(): void {
    this.modo.set('agregar');
    this.transferenciaSeleccionada.set({
      monto: 0,
      nCuentaEmisora: '',
      nCuentaReceptora: '',
      idTipoTransferencia: 1,
    });
    this.modalService.open();
  }

  abrirModalEditar(transferencia: Tranferencia) {
    this.modo.set('editar');
    this.transferenciaSeleccionada.set({
      monto: transferencia.monto,
      nCuentaEmisora: transferencia.cuentaEmisora.ncuenta,
      nCuentaReceptora: transferencia.cuentaReceptora.ncuenta,
      idTipoTransferencia: transferencia.tipoTransferencia.id,
    });
    this.modalService.open();
  }

  onGuardar(data: Partial<TranferenciaDTO>): void {
    console.log('Datos recibidos del modal:', data);
    // Convertimos a TranferenciaDTO porque el formulario ya asegura que no haya undefined
    const dto: TranferenciaDTO = {
      monto: data.monto!,
      nCuentaEmisora: data.nCuentaEmisora!,
      nCuentaReceptora: data.nCuentaReceptora!,
      idTipoTransferencia: data.idTipoTransferencia!,
    };

    this.transferenciaService.realizar(dto).subscribe({
      next: (nuevaTransferencia) => {
        console.log('Transferencia realizada:', nuevaTransferencia);
        this.modalService.close();
      },
      error: (err) => {
        console.error('Error al realizar la transferencia:', err);
      },
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
