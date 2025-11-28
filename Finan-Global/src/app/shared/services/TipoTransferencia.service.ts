import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { Tipo_Transferencia } from '../interfaces/Tipo_Transferencia-Interface';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TipoTransferenciaService {
  private apiUrl = 'http://localhost:8080/api/api/tipos-transferencia';

  tipoTransferencias = signal<Tipo_Transferencia[]>([]);

  constructor(private http: HttpClient) {
    this.inicializarData();
  }

  // Obtener todas los tipos
  getAll(): Observable<Tipo_Transferencia[]> {
    return this.http
      .get<Tipo_Transferencia[]>(this.apiUrl)
      .pipe(tap((data) => this.guardarLocal(data)));
  }

  /* Local Storage */
  private inicializarData() {
    const data = localStorage.getItem('tipoTransferencias');
    if (data) {
      this.tipoTransferencias.set(JSON.parse(data));
      console.log('tipoTransferencias cargado desde localStorage');
    }
    this.cargarTipoDocumentos();
  }

  private cargarTipoDocumentos() {
    this.getAll().subscribe();
  }

  // Guardar en Local Storage
  private guardarLocal(data: Tipo_Transferencia[]) {
    this.tipoTransferencias.set(data);
    localStorage.setItem('tipoTransferencias', JSON.stringify(data));
  }
}
