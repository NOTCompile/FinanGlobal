import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ReporteService {
  private apiUrl = 'http://localhost:8080/api/api/reportes';

  constructor(private http: HttpClient) {}

  // Reporte General
  descargarReporteGeneral(nombre: string): Observable<Blob> {
    const url = `${this.apiUrl}/generales?nombre=${nombre}`;
    return this.http.get(url, { responseType: 'blob' });
  }

  // Reporte por usuario
  descargarReportePorUsuario(nombre: string, idUsuario: number): Observable<Blob> {
    const url = `${this.apiUrl}/usuario?nombre=${nombre}&idUsuario=${idUsuario}`;
    return this.http.get(url, { responseType: 'blob' });
  }

  /*   descargarReportePDF(nombre: string): Observable<Blob> {
    console.log('Reporte generado');
    return this.http.get(`${this.apiUrl}/descargar?nombre=${nombre}`, {
      responseType: 'blob',
    });
  } */
}
