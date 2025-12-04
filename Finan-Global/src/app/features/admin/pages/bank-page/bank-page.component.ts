import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { Cuenta_Bancaria } from 'src/app/shared/interfaces/Cuenta_Bancaria-Interface';
import { CuentaBancariaService } from 'src/app/shared/services/CuentaBancaria.service';
import { ModalCuentaBancariaAdministrador } from '../../services/modalCuentaBancaria.service';
import { AddBankAdmin } from '../../components/modals/add-bank-admin/add-bank-admin';
import { Cuenta_BancariaDTO } from 'src/app/shared/interfaces/DTO/Cuenta_BancariaDTO-Interface';
import { ReporteService } from 'src/app/shared/services/Reporte.service';

@Component({
  selector: 'app-bank-page',
  imports: [AddBankAdmin],
  providers: [ModalCuentaBancariaAdministrador],
  templateUrl: './bank-page.component.html',
  styleUrl: './bank-page.component.css',
})
export default class BankPage implements OnInit {
  private cuentaBancariaServicio = inject(CuentaBancariaService);
  private modalServicio = inject(ModalCuentaBancariaAdministrador);
  private reporteService = inject(ReporteService); // PDF

  // Estado
  cuentaBancaria = this.cuentaBancariaServicio.cuentasBancarias;
  modo = signal<'agregar' | 'editar'>('agregar');
  cuentaSeleccionada = signal<Partial<Cuenta_BancariaDTO> | null>(null);

  ngOnInit(): void {
    this.cargarCuentasBancarias();
  }

  // Cargar Cuentas Bancarias
  cargarCuentasBancarias(): void {
    this.cuentaBancariaServicio.getAll().subscribe({
      next: () => {
        console.log('Cuentas Bancarias Cargadas');
        this.ordenarCuentas();
      },
      error: (err) => console.error('Error al cargar cuentas:', err),
    });
  }

  eliminarCuentaBancaria(id: number) {
    this.cuentaBancariaServicio.delete(id).subscribe({
      next: () => {
        alert('Cuenta eliminada correctamente');
      },
      error: (err) => console.error('Error al eliminar: ', err),
    });
  }

  /* Funciones */

  // Ordenar
  private ordenarCuentas(): void {
    const ordenadas = [...this.cuentaBancariaServicio.cuentasBancarias()].sort(
      (a, b) => a.id - b.id
    );
    this.cuentaBancariaServicio.cuentasBancarias.set(ordenadas);
  }

  /* Modal CRUD */

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

  // Abri modal editar
  abrirModalEditar(cuenta: Cuenta_Bancaria) {
    this.modo.set('editar');

    this.cuentaSeleccionada.set({
      id: cuenta.id,
      nombre: cuenta.nombre,
      numero_cuenta: cuenta.ncuenta,
      n_intercuenta: cuenta.n_intercuenta,
      saldo: cuenta.saldo,
      id_usuario: cuenta.usuario.id_usuario,
    });

    this.modalServicio.open();
  }

  onGuardar(cuentaData: Partial<Cuenta_BancariaDTO>): void {
    const modoActual = this.modo();

    if (modoActual === 'agregar') {
      // AGREGAR → usa DTO sin problema
      this.cuentaBancariaServicio.create(cuentaData).subscribe({
        next: () => {
          alert('Cuenta creada correctamente');
          this.cargarCuentasBancarias();
          this.modalServicio.close();
          this.ordenarCuentas();
        },
        error: (err) => console.error(err),
      });
    } else {
      // EDITAR → convertir DTO a ENTIDAD REAL
      const cuentaEntidad: Cuenta_Bancaria = {
        id: cuentaData.id!,
        nombre: cuentaData.nombre!,
        ncuenta: cuentaData.numero_cuenta!, // ← mapeo correcto
        n_intercuenta: cuentaData.n_intercuenta!,
        saldo: cuentaData.saldo!,
        usuario: {
          // ← tu backend lo NECESITA ASÍ
          id_usuario: cuentaData.id_usuario!,
          nombre: '',
          apellidos: '',
          dni_ruc: '',
          sexo: '',
          correo: '',
          telefono: '',
          direccion: '',
          nombre_usuario: '',
          contrasena: '',
          rol_usuario: 0,
        },
      };

      this.cuentaBancariaServicio.update(cuentaEntidad.id, cuentaEntidad).subscribe({
        next: () => {
          alert('Cuenta actualizada correctamente');
          this.cargarCuentasBancarias();
          this.modalServicio.close();
          this.ordenarCuentas();
        },
        error: (err) => console.error(err),
      });
    }
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
