import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { Cuenta_Bancaria } from 'src/app/shared/interfaces/Cuenta_Bancaria-Interface';
import { CuentaBancariaService } from 'src/app/shared/services/CuentaBancaria.service';
import { ModalCuentaBancariaAdministrador } from '../../services/modalCuentaBancaria.service';
import { AddBankAdmin } from '../../components/modals/add-bank-admin/add-bank-admin';
import { usuarioService } from 'src/app/shared/services/Usuario.service';

@Component({
  selector: 'app-bank-page',
  imports: [AddBankAdmin],
  providers: [ModalCuentaBancariaAdministrador],
  templateUrl: './bank-page.component.html',
  styleUrl: './bank-page.component.css',
})
export default class BankPage implements OnInit {
  private cuentaBancariaServicio = inject(CuentaBancariaService);
  private usuarioServicio = inject(usuarioService);
  private modalServicio = inject(ModalCuentaBancariaAdministrador);

  // Estado
  cuentaBancaria = this.cuentaBancariaServicio.cuentasBancarias;
  modo = signal<'agregar' | 'editar'>('agregar');
  cuentaSeleccionada = signal<Partial<Cuenta_Bancaria> | null>(null);

  ngOnInit(): void {
    this.cargarCuentasBancarias();
  }

  // Cargar Cuentas Bancarias
  cargarCuentasBancarias(): void {
    this.cuentaBancariaServicio.getAll().subscribe({
      next: () => {
        console.log('Cuentas Bancarias Cargadas');
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

  // Cargar Nombre Persona de Cuenta Bancaria
  obtenerNombreUsuario(id: number): string {
    const usuarios = this.usuarioServicio.usuarios(); // signal ya cargado
    const usuario = usuarios.find((u) => u.id_usuario === id);

    return usuario ? `${usuario.nombre} ${usuario.apellidos}` : 'Sin cliente';
  }

  /* Modal CRUD */

  // Abrir modal para agregar
  abrirModalAgregar(): void {
    console.log('Modal de Agregar Cuenta = ABIERTO');
    this.modo.set('agregar');
    this.cuentaSeleccionada.set({
      n_intercuenta: '',
      ncuenta: '',
      nombre: '',
      saldo: 4,
      id_usuario: 0,
    });
    this.modalServicio.open();
  }

  onGuardar(cuentaData: Partial<Cuenta_Bancaria>): void {
    const modoActual = this.modo();

    if (modoActual === 'agregar') {
      const nuevaCuentaBancaria: Cuenta_Bancaria = {
        ...cuentaData,
      } as Cuenta_Bancaria;

      this.cuentaBancariaServicio.create(nuevaCuentaBancaria).subscribe({
        next: () => {
          alert('Cuenta Bancaria creada correctamente');
          this.cargarCuentasBancarias(); // recarga sin reload
          this.modalServicio.close();
        },
        error: (err) => console.error('Error al crear Cuenta Bancaria:', err),
      });
    } else {
      const cuentaBase = this.cuentaSeleccionada()!;
      const cuentaBancariaEditado: Cuenta_Bancaria = {
        ...cuentaBase, // Mantiene valores previos
        ...cuentaData, // Sobrescribe nombre y usuario
      } as Cuenta_Bancaria;

      this.cuentaBancariaServicio.update(cuentaBase.id!, cuentaBancariaEditado).subscribe({
        next: () => {
          alert('Cuenta Bancaria actualizado correctamente');
          this.cargarCuentasBancarias(); // recarga sin reload
          this.modalServicio.close();
        },
        error: (err) => console.error('Error al actualizar cliente:', err),
      });
    }
  }
}
