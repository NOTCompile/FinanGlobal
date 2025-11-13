import { ChangeDetectionStrategy, Component, computed, inject, OnInit, signal } from '@angular/core';
import { Cuenta_Bancaria } from 'src/app/shared/interfaces/Cuenta_Bancaria-Interface';
import { CuentaBancariaService } from 'src/app/shared/services/CuentaBancaria.service';

@Component({
  selector: 'app-bank-page',
  imports: [],
  templateUrl: './bank-page.component.html',
  styleUrl: './bank-page.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export default class BankPage implements OnInit {
  // Servicios
  private cuentaBancariaServicio = inject(CuentaBancariaService);

  // Estado reactivo
  cuentaBancaria = signal<Cuenta_Bancaria[]>([]);

  // Computed: total de usuarios
  totalCuentasBancarias = computed(() => this.cuentaBancaria().length);

  ngOnInit(): void {
    this.cargarCuentasBancarias();
  }

  /* Carga las Cuentas Bancarias */
  cargarCuentasBancarias(): void{
     this.cuentaBancariaServicio.findAll().subscribe({
      next: (data) => {
        this.cuentaBancaria.set(data);
        localStorage.setItem('cuentasBancarias', JSON.stringify(data));
      },
      error: (err) => console.error('Error al obtener usuarios:', err),
    });
  }

}
