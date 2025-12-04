import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { Usuario } from 'src/app/shared/interfaces/Usuario-Interface';
import { CuentaBancariaService } from 'src/app/shared/services/CuentaBancaria.service';
import { EmpenoService } from 'src/app/shared/services/Empeno.service';
import { usuarioService } from 'src/app/shared/services/Usuario.service';

@Component({
  selector: 'app-stats-card-admin',
  imports: [],
  templateUrl: './stats-card-admin.html',
  styleUrl: './stats-card-admin.css',
})
export class StatsCardAdmin implements OnInit {
  private usuarioServicio = inject(usuarioService);
  private cuentaBancariaServicio = inject(CuentaBancariaService);
  private empenioService = inject(EmpenoService);

  // Estado reactivo
  usuarios = signal<Usuario[]>([]);

  // Computed: totales
  totalUsuarios = computed(() => this.usuarios().length);
  totalCuentasBancarias = this.cuentaBancariaServicio.totalCuentas;
  totalEmpenios = this.empenioService.totalEmpenos;

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  /** Carga usuarios de rol 4 (clientes) */
  cargarUsuarios(): void {
    console.log('Cargando clientes totales...');
    this.usuarioServicio.findByRol(4).subscribe({
      next: (data) => {
        this.usuarios.set(data);
      },
      error: (err) => console.error('Error al obtener total de clientes:', err),
    });
  }
}
