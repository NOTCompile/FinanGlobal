import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { Tarjeta } from '../interfaces/Tarjetas-Interface';
import { TarjetaDTO } from '../interfaces/DTO/TarjetaDTO-Interface';

@Injectable({
  providedIn: 'root',
})
export class TarjetaService {
  private apiUrl = 'http://localhost:8080/api/api/tarjetas';

  // Estado local
  tarjetaBancaria = signal<Tarjeta[]>([]);

  constructor(private http: HttpClient) {
    this.inicializarData();
  }

  // Obtener todas las tarjetas
  getAll(): Observable<Tarjeta[]> {
    return this.http.get<Tarjeta[]>(this.apiUrl).pipe(tap((data) => this.guardarLocal(data)));
  }

  // Obtener por ID
  getById(id: number): Observable<Tarjeta> {
    return this.http.get<Tarjeta>(`${this.apiUrl}/${id}`);
  }

  // Obtener tarjetas por ID de cuenta bancaria
  getByCuenta(idCuenta: number): Observable<Tarjeta[]> {
    return this.http.get<Tarjeta[]>(`${this.apiUrl}/cuenta/${idCuenta}`);
  }

  // Obtener cuentas por ID de usuario desde la data local
  getByUsuarioLocal(idUsuario: number): Tarjeta[] {
    return this.tarjetaBancaria().filter((cuenta) => cuenta.cuentaBancaria.id === idUsuario);
  }

  // Crear tarjeta
  create(dto: TarjetaDTO): Observable<Tarjeta> {
    return this.http.post<Tarjeta>(this.apiUrl, dto).pipe(
      tap((nueva) => {
        this.tarjetaBancaria.set([...this.tarjetaBancaria(), nueva]);
      })
    );
  }

  // Actualizar tarjeta
  update(id: number, dto: TarjetaDTO): Observable<Tarjeta> {
    return this.http.put<Tarjeta>(`${this.apiUrl}/${id}`, dto).pipe(
      tap((actualizada) => {
        const lista = this.tarjetaBancaria().map((t) => (t.id === id ? actualizada : t));
        this.tarjetaBancaria.set(lista);
      })
    );
  }

  // Eliminar tarjeta
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      tap(() => {
        const lista = this.tarjetaBancaria().filter((t) => t.id !== id);
        this.guardarLocal(lista);
      })
    );
  }

  /* LS */
  private inicializarData(): void {
    const data = localStorage.getItem('Tarjetas');
    if (data) {
      this.tarjetaBancaria.set(JSON.parse(data));
      console.log('Tarjetas cargadas desde localStorage');
    }
    this.cargarTarjetas();
  }

  private cargarTarjetas(): void {
    this.getAll().subscribe();
  }

  private guardarLocal(data: Tarjeta[]): void {
    this.tarjetaBancaria.set(data);
    localStorage.setItem('Tarjetas', JSON.stringify(data));
  }
}
