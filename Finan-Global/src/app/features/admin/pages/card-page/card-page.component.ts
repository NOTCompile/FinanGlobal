import { Component, inject, OnInit, signal } from '@angular/core';
import { TarjetaDTO } from 'src/app/shared/interfaces/DTO/TarjetaDTO-Interface';
import { Tarjeta } from 'src/app/shared/interfaces/Tarjetas-Interface';
import { ReporteService } from 'src/app/shared/services/Reporte.service';
import { TarjetaService } from 'src/app/shared/services/Tarjetas.service';
import { ModalTarjetaAdministrador } from '../../services/modalTarjeta.service';
import { AddCardAdmin } from '../../components/modals/add-card-admin/add-card-admin';

@Component({
  selector: 'app-card-page',
  imports: [AddCardAdmin],
  providers: [ModalTarjetaAdministrador],
  templateUrl: './card-page.component.html',
  styleUrl: './card-page.component.css',
})
export default class CardPageComponent implements OnInit {
  private tarjetaBancariaServicio = inject(TarjetaService);
  private modalServicio = inject(ModalTarjetaAdministrador);
  private reporteService = inject(ReporteService); // PDF

  ngOnInit(): void {
    this.cargarTarjetasBancarias();
  }

  // Estado
  tarjetaBancaria = this.tarjetaBancariaServicio.tarjetaBancaria;
  modo = signal<'agregar' | 'editar'>('agregar');
  tarjetaBancariaSeleccionada = signal<Partial<Tarjeta> | null>(null);

  // Cargar Cuentas Bancarias
  cargarTarjetasBancarias(): void {
    this.tarjetaBancariaServicio.getAll().subscribe({
      next: () => {
        console.log('Tarjetas Bancarias Cargadas');
        this.ordenarCuentas();
      },
      error: (err) => console.error('Error al cargar cuentas:', err),
    });
  }
  // Ordenar
  private ordenarCuentas(): void {
    const ordenadas = [...this.tarjetaBancariaServicio.tarjetaBancaria()].sort(
      (a, b) => a.id - b.id
    );
    this.tarjetaBancariaServicio.tarjetaBancaria.set(ordenadas);
  }
  /* CRUD */
  abrirModalAgregar(): void {
    this.modo.set('agregar');
    this.tarjetaBancariaSeleccionada.set({
      id: 0,

      codigo: '',
      cvc: '',
      fechaCaducidad: new Date(),
    });
    this.modalServicio.open();
  }

  abrirModalEditar(tarjeta: Tarjeta) {
    this.modo.set('editar');

    this.tarjetaBancariaSeleccionada.set({
      id: tarjeta.id,
      cuentaBancaria: tarjeta.cuentaBancaria,
      tipoTarjeta: tarjeta.tipoTarjeta,
      codigo: tarjeta.codigo,
      cvc: tarjeta.cvc,
      fechaCaducidad: tarjeta.fechaCaducidad,
    });

    this.modalServicio.open();
  }

  onGuardar(dto: TarjetaDTO) {
    const modoActual = this.modo();

    if (modoActual === 'agregar') {
      this.tarjetaBancariaServicio.create(dto).subscribe({
        next: () => {
          alert('Tarjeta creada correctamente');
          this.cargarTarjetasBancarias();
          this.modalServicio.close();
          this.ordenarCuentas();
        },
        error: (err) => console.error(err),
      });
    }
  }

  eliminarTarjetaBancaria(id: number) {
    this.tarjetaBancariaServicio.delete(id).subscribe({
      next: () => {
        alert('Tarjeta eliminada correctamente');
      },
      error: (err) => console.error('Error al eliminar: ', err),
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
