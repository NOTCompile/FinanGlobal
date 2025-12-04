import { Injectable, signal } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Tipo_Tarjeta } from '../interfaces/Tipo_Tarjeta-Interface';

@Injectable({
  providedIn: 'root',
})
export class TipoTarjetaService {
  private apiUrl = 'http://localhost:8080/api/api/tipo-tarjetas';

  constructor(private http: HttpClient) {
    this.inicializarData();
  }

  tipoTarjeta = signal<Tipo_Tarjeta[]>([]); // Obtener todos
  getAll(): Observable<Tipo_Tarjeta[]> {
    return this.http.get<Tipo_Tarjeta[]>(this.apiUrl).pipe(tap((data) => this.guardarLocal(data)));
  }

  // Obtener por id
  getById(id: number): Observable<Tipo_Tarjeta> {
    return this.http.get<Tipo_Tarjeta>(`${this.apiUrl}/${id}`);
  }

  // Crear
  create(tipo: Tipo_Tarjeta): Observable<Tipo_Tarjeta> {
    return this.http.post<Tipo_Tarjeta>(this.apiUrl, tipo).pipe(
      tap((nuevo) => {
        const lista = [...this.tipoTarjeta(), nuevo];
        this.guardarLocal(lista);
      })
    );
  }

  // Actualizar
  update(id: number, tipo: Tipo_Tarjeta): Observable<Tipo_Tarjeta> {
    return this.http.put<Tipo_Tarjeta>(`${this.apiUrl}/${id}`, tipo).pipe(
      tap((actualizado) => {
        const lista = this.tipoTarjeta().map((t) => (t.id === id ? actualizado : t));
        this.guardarLocal(lista);
      })
    );
  }

  // Eliminar
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      tap(() => {
        const lista = this.tipoTarjeta().filter((t) => t.id !== id);
        this.guardarLocal(lista);
      })
    );
  }

  /* LS */

  private inicializarData(): void {
    const data = localStorage.getItem('TipoTarjetas');
    if (data) {
      this.tipoTarjeta.set(JSON.parse(data));
      console.log('TipoTarjetas cargadas desde localStorage');
    }
    this.cargarTipos();
  }

  private cargarTipos(): void {
    this.getAll().subscribe();
  }

  private guardarLocal(data: Tipo_Tarjeta[]): void {
    this.tipoTarjeta.set(data);
    localStorage.setItem('TipoTarjetas', JSON.stringify(data));
  }
}
