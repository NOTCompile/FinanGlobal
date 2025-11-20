import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { map, Observable, tap } from 'rxjs';
import { Cuenta_Bancaria } from '../interfaces/Cuenta_Bancaria-Interface';

@Injectable({
  providedIn: 'root',
})
export class CuentaBancariaService {
  private apiUrl = 'http://localhost:8080/api/api/cuentas';
  cuentaBancaria = signal<Cuenta_Bancaria[]>([]);

  constructor(private http: HttpClient) {}

  /* ---------- Helpers de mapeo ---------- */

  private mapFromBackend(raw: any): Cuenta_Bancaria {
    return {
      id_cuenta_bancaria: raw.id,
      nombre: raw.nombre,
      n_cuenta: raw.ncuenta,
      n_intercuenta: raw.n_intercuenta,
      saldo: raw.saldo,
      id_usuario: raw.usuario,
    };
  }

  private mapToBackend(c: Cuenta_Bancaria) {
    return {
      id: c.id_cuenta_bancaria,
      nombre: c.nombre,
      ncuenta: c.n_cuenta,
      n_intercuenta: c.n_intercuenta,
      saldo: c.saldo,
      usuario: c.id_usuario,
    };
  }

  /* ---------- CRUD ---------- */

  findAll(): Observable<Cuenta_Bancaria[]> {
    return this.http.get<any[]>(this.apiUrl).pipe(
      map((list) => list.map((c) => this.mapFromBackend(c))),
      tap((mapped) => this.guardarEnLocalStorage(mapped))
    );
  }

  getById(id: number): Observable<Cuenta_Bancaria> {
    return this.http.get<any>(`${this.apiUrl}/${id}`).pipe(map((raw) => this.mapFromBackend(raw)));
  }

  create(cuenta: Cuenta_Bancaria): Observable<Cuenta_Bancaria> {
    return this.http.post<any>(this.apiUrl, this.mapToBackend(cuenta)).pipe(
      map((raw) => this.mapFromBackend(raw)),
      tap(() => this.cargarCuenta_Bancarias())
    );
  }

  update(cuenta: Cuenta_Bancaria): Observable<Cuenta_Bancaria> {
    return this.http.put<any>(`${this.apiUrl}/${cuenta.id_cuenta_bancaria}`, this.mapToBackend(cuenta)).pipe(
      map((raw) => this.mapFromBackend(raw)),
      tap(() => this.cargarCuenta_Bancarias())
    );
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(tap(() => this.eliminarLocal(id)));
  }

  private guardarEnLocalStorage(data: Cuenta_Bancaria[]) {
    this.cuentaBancaria.set(data);
    localStorage.setItem('cuentasBancarias', JSON.stringify(data));
  }

  cargarCuenta_Bancarias() {
    this.findAll().subscribe();
  }

  private eliminarLocal(id: number) {
    const updated = this.cuentaBancaria().filter((c) => c.id_cuenta_bancaria !== id);
    this.guardarEnLocalStorage(updated);
  }
}
