import { Component, inject, OnInit, signal } from '@angular/core';
import { ModalCuentaBancariaAdministrador } from 'src/app/features/admin/services/modalCuentaBancaria.service';
import { TarjetaDTO } from 'src/app/shared/interfaces/DTO/TarjetaDTO-Interface';
import { Tarjeta } from 'src/app/shared/interfaces/Tarjetas-Interface';
import { Usuario } from 'src/app/shared/interfaces/Usuario-Interface';
import { AuthService } from 'src/app/shared/services/authService';
import { CuentaBancariaService } from 'src/app/shared/services/CuentaBancaria.service';
import { ReporteService } from 'src/app/shared/services/Reporte.service';
import { TarjetaService } from 'src/app/shared/services/Tarjetas.service';

@Component({
  selector: 'app-cards-page',
  imports: [],
  providers: [ModalCuentaBancariaAdministrador],
  templateUrl: './cards-page.component.html',
  styleUrl: './cards-page.component.css',
})
export default class CardsPageComponent implements OnInit {
  private reporteService = inject(ReporteService);
  private authService = inject(AuthService);
  private cuentaService = inject(CuentaBancariaService);
  private modalServicio = inject(ModalCuentaBancariaAdministrador);

  usuarioActual!: Usuario | null;
  tarjetasUsuario: Tarjeta[] = [];
  modo = signal<'agregar' | 'editar'>('agregar');
  tarjetaBancariaSeleccionada = signal<Partial<Tarjeta> | null>(null);

  ngOnInit(): void {
    this.obtenerUsuarioLS();
  }

  obtenerUsuarioLS() {
    this.usuarioActual = this.authService.usuario();
  }

  // Abrir modal para agregar
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

  onGuardar(cuentaData: Partial<TarjetaDTO>): void {
    const modoActual = this.modo();

    if (modoActual === 'agregar') {
      // AGREGAR → usa DTO sin problema
      this.cuentaService.create(cuentaData).subscribe({
        next: () => {
          alert('Cuenta creada correctamente');
          this.modalServicio.close();
        },
        error: (err) => console.error(err),
      });
    }
  }

  obtenerPDFUsuario(nombreReporte: string, idUsuario?: number) {
    if (!idUsuario) {
      console.error('No existe un ID de usuario válido');
      return;
    }

    this.reporteService.descargarReportePorUsuario(nombreReporte, idUsuario).subscribe({
      next: (data: Blob) => {
        const fileURL = URL.createObjectURL(data);
        window.open(fileURL, '_blank'); // Abre el PDF
      },
      error: (err) => console.error('Error al generar PDF:', err),
    });
  }
}
