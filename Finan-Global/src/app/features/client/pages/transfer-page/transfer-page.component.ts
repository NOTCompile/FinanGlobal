import { Component, inject, OnInit } from '@angular/core';
import { Usuario } from 'src/app/shared/interfaces/Usuario-Interface';
import { AuthService } from 'src/app/shared/services/authService';
import { ReporteService } from 'src/app/shared/services/Reporte.service';

@Component({
  selector: 'app-transfer-page',
  imports: [],
  templateUrl: './transfer-page.component.html',
  styleUrl: './transfer-page.component.css',
})
export default class TransferPageComponent implements OnInit {
  private reporteService = inject(ReporteService);
  private authService = inject(AuthService);
  usuarioActual!: Usuario | null;
  ngOnInit(): void {
    this.obtenerUsuarioLS();
  }

  obtenerUsuarioLS() {
    this.usuarioActual = this.authService.usuario();
  }
  obtenerPDFUsuario(nombreReporte: string, idUsuario?: number) {
    if (!idUsuario) {
      console.error('No existe un ID de usuario válido');
      return;
    }

    this.reporteService.descargarReportePorUsuario(nombreReporte, idUsuario).subscribe({
      next: (data: Blob) => {
        const fileURL = URL.createObjectURL(data);
        window.open(fileURL, '_blank'); // Abre el PDF
      },
      error: (err) => console.error('Error al generar PDF:', err),
    });
  }
}
