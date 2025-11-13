import { ChangeDetectionStrategy, Component } from '@angular/core';
import { WelcomeAdmin } from '../../components/welcome-admin/welcome-admin';
import { StatsCardAdmin } from '../../components/stats-card-admin/stats-card-admin';
import { ReporteService } from 'src/app/shared/services/Reporte.service';

@Component({
  selector: 'app-dashboard-page',
  imports: [WelcomeAdmin, StatsCardAdmin],
  templateUrl: './dashboard-page.component.html',
  styleUrl: './dashboard-page.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export default class DashboardPageComponent {

  constructor(private reporteService: ReporteService) {}

  verPDF(nombre: string) {
    this.reporteService.descargarReportePDF(nombre).subscribe({
      next: (data: Blob) => {
        const file = new Blob([data], { type: 'application/pdf' });
        const fileURL = URL.createObjectURL(file);
        window.open(fileURL, '_blank');
      },
      error: (err) => console.error('Error al mostrar PDF:', err),
    });
  }
}
