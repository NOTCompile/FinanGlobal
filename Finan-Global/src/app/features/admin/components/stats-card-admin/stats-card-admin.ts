import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { Usuario } from 'src/app/shared/interfaces/Usuario-Interface';
import { usuarioService } from 'src/app/shared/services/Usuario.service';

@Component({
  selector: 'app-stats-card-admin',
  imports: [],
  templateUrl: './stats-card-admin.html',
  styleUrl: './stats-card-admin.css',
})
export class StatsCardAdmin implements OnInit {
  private usuarioServicio = inject(usuarioService);

  // Estado reactivo
  usuarios = signal<Usuario[]>([]);

  // Computed: total de usuarios
  totalUsuarios = computed(() => this.usuarios().length);

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
      error: (err) => console.error('Error al obtener usuarios:', err),
    });
  }
}
