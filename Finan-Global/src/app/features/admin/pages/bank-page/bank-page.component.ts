import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { Cuenta_Bancaria } from 'src/app/shared/interfaces/Cuenta_Bancaria-Interface';
import { CuentaBancariaService } from 'src/app/shared/services/CuentaBancaria.service';

@Component({
  selector: 'app-bank-page',
  imports: [],
  templateUrl: './bank-page.component.html',
  styleUrl: './bank-page.component.css',
})
export default class BankPage implements OnInit {
  private cuentaBancariaServicio = inject(CuentaBancariaService);

  // Estado
  cuentaBancaria = signal<Cuenta_Bancaria[]>([]);

  totalCuentasBancarias = computed(() => this.cuentaBancaria().length);

  ngOnInit(): void {
    this.cargarCuentasBancarias();
  }

  cargarCuentasBancarias(): void {
    this.cuentaBancariaServicio.findAll().subscribe({
      next: (data) => {
        this.cuentaBancaria.set(data); // <<--- AQUÍ CARGAS AL STATE
      },
      error: (err) => console.error('Error al cargar cuentas:', err),
    });
  }
}
