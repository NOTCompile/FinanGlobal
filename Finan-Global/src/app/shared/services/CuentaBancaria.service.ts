import { HttpClient } from '@angular/common/http';
import { computed, Injectable, signal } from '@angular/core';
import { map, Observable, tap } from 'rxjs';
import { Cuenta_Bancaria } from '../interfaces/Cuenta_Bancaria-Interface';

@Injectable({
  providedIn: 'root',
})
export class CuentaBancariaService {
  private apiUrl = 'http://localhost:8080/api/api/cuentas';

  // Estado global reactivo
  cuentasBancarias = signal<Cuenta_Bancaria[]>([]);

  constructor(private http: HttpClient) {
    this.inicializarData();
  }

  // Obtener todas las cuentas
  getAll(): Observable<Cuenta_Bancaria[]> {
    return this.http
      .get<Cuenta_Bancaria[]>(this.apiUrl)
      .pipe(tap((data) => this.guardarLocal(data)));
  }

  // Obtener una cuenta por ID
  getById(id: number): Observable<Cuenta_Bancaria> {
    return this.http.get<Cuenta_Bancaria>(`${this.apiUrl}/${id}`);
  }

  // Crear una nueva cuenta (ENVÍA DTO)
  create(cuenta: Partial<Cuenta_Bancaria>): Observable<Cuenta_Bancaria> {
    return this.http.post<Cuenta_Bancaria>(this.apiUrl, cuenta);
  }

  // Actualizar cuenta completa
  update(id: number, cuenta: Cuenta_Bancaria): Observable<Cuenta_Bancaria> {
    return this.http.put<Cuenta_Bancaria>(`${this.apiUrl}/${id}`, cuenta);
  }

  // Eliminar cuenta
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(tap(() => this.eliminarLocal(id)));
  }

  /* Funciones Página */

  // Total de Cuentas
  totalCuentas = computed(() => this.cuentasBancarias().length);

  /* Persistencia Local Storage */
  private inicializarData() {
    const data = localStorage.getItem('cuentasBancarias');
    if (data) {
      this.cuentasBancarias.set(JSON.parse(data));
      console.log('Cuentas cargadas desde localStorage');
    }
    this.cargarCuentas();
  }

  private guardarLocal(data: Cuenta_Bancaria[]) {
    this.cuentasBancarias.set(data);
    localStorage.setItem('cuentasBancarias', JSON.stringify(data));
  }

  private cargarCuentas() {
    this.getAll().subscribe();
  }

  private eliminarLocal(id: number) {
    const updated = this.cuentasBancarias().filter((c) => c.id !== id);
    this.guardarLocal(updated);
  }

  eliminarLocalStorage(id: number) {
    this.eliminarLocal(id);
  }
}
