import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { Cuenta_Bancaria } from 'src/app/shared/interfaces/Cuenta_Bancaria-Interface';
import { CuentaBancariaService } from 'src/app/shared/services/CuentaBancaria.service';
import { ModalCuentaBancariaAdministrador } from '../../services/modalCuentaBancaria.service';
import { AddBankAdmin } from '../../components/modals/add-bank-admin/add-bank-admin';

@Component({
  selector: 'app-bank-page',
  imports: [AddBankAdmin],
  templateUrl: './bank-page.component.html',
  styleUrl: './bank-page.component.css',
})
export default class BankPage implements OnInit {
  private cuentaBancariaServicio = inject(CuentaBancariaService);
  private modalService = inject(ModalCuentaBancariaAdministrador);

  // Estado
  cuentaBancaria = signal<Cuenta_Bancaria[]>([]);
  modo = signal<'agregar' | 'editar'>('agregar');
  cuentabancariaSeleccionada = signal<Partial<Cuenta_Bancaria> | null>(null);

  totalCuentasBancarias = computed(() => this.cuentaBancaria().length);

  ngOnInit(): void {
    this.cargarCuentasBancarias();
  }

  /* Cargar Cuentas Bancarias */
  cargarCuentasBancarias(): void {
    this.cuentaBancariaServicio.findAll().subscribe({
      next: (data) => {
        this.cuentaBancaria.set(data);
        console.log('Cuentas Bancarias Cargadas');
      },
      error: (err) => console.error('Error al cargar cuentas:', err),
    });
  }

  /* Modal */
  abrirModalAgregar(): void {
    this.modo.set('agregar');
    this.cuentabancariaSeleccionada.set({
      n_intercuenta: '',
      n_cuenta: '',
      nombre: '',
      saldo: 0.0,
      id_usuario: 0,
    });
    this.modalService.open();
    console.log('Modal Abierto');
  }

  /* Abrir modal para editar cliente existente */
  abrirModalEditar(cuentabancaria: Cuenta_Bancaria): void {
    this.modo.set('editar');
    this.cuentabancariaSeleccionada.set(cuentabancaria);
    this.modalService.open();
  }

  /* Guardar (crear o actualizar) usuario */
  onGuardar(cuentabancariaData: Partial<Cuenta_Bancaria>): void {
    console.log('Datos usuario', cuentabancariaData);
    const modoActual = this.modo();

    if (modoActual === 'agregar') {
      const nuevoUsuario: Cuenta_Bancaria = {
        ...cuentabancariaData,
        id_usuario: 0, // backend lo asigna
        nombre_usuario: cuentabancariaData.nombre ?? '',
      } as Cuenta_Bancaria;

      this.cuentaBancariaServicio.create(nuevoUsuario).subscribe({
        next: () => {
          alert('Usuario creado correctamente');
          this.cargarCuentasBancarias(); // recarga sin reload
          this.modalService.close();
        },
        error: (err) => console.error('Error al crear cliente:', err),
      });
    } else {
      const usuarioEditado: Cuenta_Bancaria = {
        ...this.cuentabancariaSeleccionada()!,
        ...cuentabancariaData,
      } as Cuenta_Bancaria;

      this.cuentaBancariaServicio.update(usuarioEditado).subscribe({
        next: () => {
          alert('Usuario actualizado correctamente');
          this.cargarCuentasBancarias(); // recarga sin reload
          this.modalService.close();
        },
        error: (err) => console.error('Error al actualizar cliente:', err),
      });
    }
  }
}
