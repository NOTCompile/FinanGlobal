import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { Cuenta_Bancaria } from '../interfaces/Cuenta_Bancaria-Interface';

@Injectable({
  providedIn: 'root',
})
export class CuentaBancariaService {
  private apiUrl = 'http://localhost:8080/api/api/cuentas';
  cuentaBancaria = signal<Cuenta_Bancaria[]>([]);

  constructor(private http: HttpClient) {}

  // CRUD
  /* Obtener todos los Cuenta_Bancarias */
  findAll(): Observable<Cuenta_Bancaria[]> {
    return this.http
      .get<Cuenta_Bancaria[]>(this.apiUrl)
      .pipe(tap((data) => this.guardarEnLocalStorage(data)));
  }

  /*  Obtener Cuenta_Bancaria por ID - GET */
  getById(id: number): Observable<Cuenta_Bancaria> {
    return this.http.get<Cuenta_Bancaria>(`${this.apiUrl}/${id}`);
  }

  /* Crear nuevo Cuenta_Bancaria - POST */
  create(Cuenta_Bancaria: Cuenta_Bancaria): Observable<Cuenta_Bancaria> {
    return this.http.post<Cuenta_Bancaria>(this.apiUrl, Cuenta_Bancaria).pipe(tap(() => this.cargarCuenta_Bancarias()));
  }

  /* Actualizar Cuenta_Bancaria existente - PUT */
  update(Cuenta_Bancaria: Partial<Cuenta_Bancaria>): Observable<Cuenta_Bancaria> {
    return this.http
      .put<Cuenta_Bancaria>(`${this.apiUrl}/${Cuenta_Bancaria.id_cuenta_bancaria}`, Cuenta_Bancaria)
      .pipe(tap(() => this.cargarCuenta_Bancarias()));
  }

  /* Eliminar Cuenta_Bancaria - DELETE */
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(tap(() => this.eliminarLocal(id)));
  }

  /* Buscar Cuenta_Bancaria por correo - GET */
  findByCorreo(correo: string): Observable<Cuenta_Bancaria> {
    return this.http.get<Cuenta_Bancaria>(`${this.apiUrl}/email/${correo}`);
  }

  /* Filtrar Cuenta_Bancarias por rol */
  findByRol(rol: number): Observable<Cuenta_Bancaria[]> {
    return this.http
      .get<Cuenta_Bancaria[]>(`${this.apiUrl}/tipo/${rol}`)
      .pipe(tap((data) => this.guardarEnLocalStorage(data)));
  }

  // PERSISTENCIA
  private inicializarData() {
    const data = localStorage.getItem('cuentasBancarias');
    if (data) {
      this.cuentaBancaria.set(JSON.parse(data));
      console.log('cuentasBancarias cargados desde localStorage');
    }

    this.cargarCuenta_Bancarias();
  }

  /* Guardar signal + localStorage */
  private guardarEnLocalStorage(data: Cuenta_Bancaria[]) {
    this.cuentaBancaria.set(data);
    localStorage.setItem('cuentasBancarias', JSON.stringify(data));
  }

  /* Cargar Cuenta_Bancarias backend */
  cargarCuenta_Bancarias() {
    this.http.get<Cuenta_Bancaria[]>(this.apiUrl).subscribe({
      next: (data) => this.guardarEnLocalStorage(data),
      error: (err) => console.error('Error al cargar cuentasBancarias desde el backend', err),
    });
  }

  /* Eliminar Cuenta_Bancaria localmente */
  private eliminarLocal(id: number) {
    const updated = this.cuentaBancaria().filter((u) => u.id_cuenta_bancaria !== id);
    this.guardarEnLocalStorage(updated);
  }
}
