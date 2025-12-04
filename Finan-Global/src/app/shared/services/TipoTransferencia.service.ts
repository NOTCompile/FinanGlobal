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

  // Obtener por ID
  getById(id: number): Observable<Tipo_Transferencia> {
    return this.http.get<Tipo_Transferencia>(`${this.apiUrl}/${id}`);
  }

  // Crear nuevo
  create(tipo: Omit<Tipo_Transferencia, 'id'>): Observable<Tipo_Transferencia> {
    return this.http.post<Tipo_Transferencia>(this.apiUrl, tipo).pipe(
      tap((nuevo) => {
        const lista = [...this.tipoTransferencias(), nuevo];
        this.guardarLocal(lista);
      })
    );
  }

  // Actualizar existente
  update(id: number, tipo: Tipo_Transferencia): Observable<Tipo_Transferencia> {
    return this.http.put<Tipo_Transferencia>(`${this.apiUrl}/${id}`, tipo).pipe(
      tap((actualizado) => {
        const lista = this.tipoTransferencias().map((t) => (t.id === id ? actualizado : t));
        this.guardarLocal(lista);
      })
    );
  }

  // Eliminar
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      tap(() => {
        const lista = this.tipoTransferencias().filter((t) => t.id !== id);
        this.guardarLocal(lista);
      })
    );
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
