import { Component, inject, OnInit, signal } from '@angular/core';
import { AddBankAdmin } from 'src/app/features/admin/components/modals/add-bank-admin/add-bank-admin';
import { ModalCuentaBancariaAdministrador } from 'src/app/features/admin/services/modalCuentaBancaria.service';
import { Cuenta_Bancaria } from 'src/app/shared/interfaces/Cuenta_Bancaria-Interface';
import { Cuenta_BancariaDTO } from 'src/app/shared/interfaces/DTO/Cuenta_BancariaDTO-Interface';
import { Usuario } from 'src/app/shared/interfaces/Usuario-Interface';
import { AuthService } from 'src/app/shared/services/authService';
import { CuentaBancariaService } from 'src/app/shared/services/CuentaBancaria.service';
import { ReporteService } from 'src/app/shared/services/Reporte.service';

@Component({
  selector: 'app-account-page',
  imports: [AddBankAdmin],
  providers: [ModalCuentaBancariaAdministrador],
  templateUrl: './account-page.component.html',
  styleUrl: './account-page.component.css',
})
export default class AccountPageComponent implements OnInit {
  private reporteService = inject(ReporteService);
  private authService = inject(AuthService);
  private cuentaService = inject(CuentaBancariaService);
  private modalServicio = inject(ModalCuentaBancariaAdministrador);

  usuarioActual!: Usuario | null;
  cuentasUsuario: Cuenta_Bancaria[] = [];
  modo = signal<'agregar' | 'editar'>('agregar');
  cuentaSeleccionada = signal<Partial<Cuenta_BancariaDTO> | null>(null);

  ngOnInit(): void {
    this.obtenerUsuarioLS();
    this.obtenerCBUsuario();
  }

  obtenerUsuarioLS() {
    this.usuarioActual = this.authService.usuario();
  }

  obtenerCBUsuario() {
    if (!this.usuarioActual) return;

    this.cuentasUsuario = this.cuentaService.getByUsuarioLocal(this.usuarioActual.id_usuario);

    console.log('Cuentas del usuario:', this.cuentasUsuario);
  }

  // Abrir modal para agregar
  abrirModalAgregar(): void {
    console.log('Modal de Agregar Cuenta = ABIERTO');
    this.modo.set('agregar');
    this.cuentaSeleccionada.set({
      nombre: '',
      numero_cuenta: '',
      n_intercuenta: '',
      saldo: 0,
      id_usuario: 0,
    });
    this.modalServicio.open();
  }

  onGuardar(cuentaData: Partial<Cuenta_BancariaDTO>): void {
    const modoActual = this.modo();

    if (modoActual === 'agregar') {
      // AGREGAR → usa DTO sin problema
      this.cuentaService.create(cuentaData).subscribe({
        next: () => {
          alert('Cuenta creada correctamente');
          this.obtenerCBUsuario();
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
