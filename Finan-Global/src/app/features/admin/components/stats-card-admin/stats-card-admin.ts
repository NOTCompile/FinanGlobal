import { ChangeDetectionStrategy, Component, computed, inject, signal } from '@angular/core';
import { Usuario } from 'src/app/shared/interfaces/Usuario-Interface';
import { usuarioService } from 'src/app/shared/services/usuarioService';

@Component({
  selector: 'app-stats-card-admin',
  imports: [],
  templateUrl: './stats-card-admin.html',
  styleUrl: './stats-card-admin.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class StatsCardAdmin {
  private usuarioServicio = inject(usuarioService);

  // Estado reactivo
  usuarios = signal<Usuario[]>([]);

  // Computed: total de usuarios
  totalUsuarios = computed(() => this.usuarios.length);

  /** Carga usuarios de rol 4 (clientes) */
  cargarUsuarios(): void {
    this.usuarioServicio.findByRol(4).subscribe({
      next: (data) => {
        this.usuarios.set(data);
        localStorage.setItem('usuarios', JSON.stringify(data));
      },
      error: (err) => console.error('Error al obtener usuarios:', err),
    });
  }
}
