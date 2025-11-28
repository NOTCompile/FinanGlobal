import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { Cuenta_Bancaria } from 'src/app/shared/interfaces/Cuenta_Bancaria-Interface';
import { CuentaBancariaService } from 'src/app/shared/services/CuentaBancaria.service';
import { ModalCuentaBancariaAdministrador } from '../../services/modalCuentaBancaria.service';
import { AddBankAdmin } from '../../components/modals/add-bank-admin/add-bank-admin';
import { usuarioService } from 'src/app/shared/services/Usuario.service';

@Component({
  selector: 'app-bank-page',
  imports: [],
  templateUrl: './bank-page.component.html',
  styleUrl: './bank-page.component.css',
})
export default class BankPage implements OnInit {
  private cuentaBancariaServicio = inject(CuentaBancariaService);
  private usuarioServicio = inject(usuarioService);

  // Estado
  cuentaBancaria = this.cuentaBancariaServicio.cuentasBancarias;

  ngOnInit(): void {
    this.cargarCuentasBancarias();
  }

  /* Cargar Cuentas Bancarias */
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
    const usuarios = this.usuarioServicio.usuarios(); // ← signal ya cargado
    const usuario = usuarios.find((u) => u.id_usuario === id);

    return usuario ? `${usuario.nombre} ${usuario.apellidos}` : 'Sin cliente';
  }
}
