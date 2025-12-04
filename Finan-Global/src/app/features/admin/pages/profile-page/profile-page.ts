import { Component, inject, OnInit } from '@angular/core';
import { Usuario } from 'src/app/shared/interfaces/Usuario-Interface';
import { AuthService } from 'src/app/shared/services/authService';
import { usuarioService } from 'src/app/shared/services/Usuario.service';

@Component({
  selector: 'app-profile-page',
  imports: [],
  templateUrl: './profile-page.html',
  styleUrl: './profile-page.css',
})
export default class ProfilePage implements OnInit {
  private authService = inject(AuthService);

  usuarioActual!: Usuario | null;

  ngOnInit(): void {
    this.obtenerUsuarioLS();
  }

  obtenerUsuarioLS() {
    this.usuarioActual = this.authService.usuario();
  }
}
