import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ReporteService {
  private apiUrl = 'http://localhost:8080/api/api/reportes';

  constructor(private http: HttpClient) {}

  descargarReportePDF(nombre: string): Observable<Blob> {
    console.log('Reporte generado');
    return this.http.get(`${this.apiUrl}/descargar?nombre=${nombre}`, {
      responseType: 'blob',
    });
  }
}
