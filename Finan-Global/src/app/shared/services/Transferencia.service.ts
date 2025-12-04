import { Injectable, signal } from '@angular/core';
import { Tranferencia } from '../interfaces/Transferencia-Interface';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { TranferenciaDTO } from '../interfaces/DTO/TransferenciaDTO-Interface';

@Injectable({
  providedIn: 'root',
})
export class TransferenciaService {
  private apiUrl = 'http://localhost:8080/api/api/transferencias';

  transferencias = signal<Tranferencia[]>([]);

  constructor(private http: HttpClient) {
    this.inicializarData();
  }

  // Obtener todas los tipos
  getAll(): Observable<Tranferencia[]> {
    return this.http.get<Tranferencia[]>(this.apiUrl).pipe(tap((data) => this.guardarLocal(data)));
  }

  // Obtener transferencia por ID
  getById(id: number): Observable<Tranferencia> {
    return this.http.get<Tranferencia>(`${this.apiUrl}/${id}`);
  }

  // Realizar transferencia
  realizar(dto: TranferenciaDTO): Observable<Tranferencia> {
    return this.http.post<Tranferencia>(`${this.apiUrl}/realizar`, dto).pipe(
      tap((nueva) => {
        const actualizadas = [...this.transferencias(), nueva];
        this.guardarLocal(actualizadas);
      })
    );
  }

  /* Local Storage */
  private inicializarData() {
    const data = localStorage.getItem('Transferencias');
    if (data) {
      this.transferencias.set(JSON.parse(data));
      console.log('Transferencias cargado desde localStorage');
    }
    this.cargarTipoDocumentos();
  }

  private cargarTipoDocumentos() {
    this.getAll().subscribe();
  }

  // Guardar en Local Storage
  private guardarLocal(data: Tranferencia[]) {
    this.transferencias.set(data);
    localStorage.setItem('Transferencias', JSON.stringify(data));
  }
}
