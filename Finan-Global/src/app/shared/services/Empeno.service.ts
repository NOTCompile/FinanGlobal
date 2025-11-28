import { HttpClient } from '@angular/common/http';
import { computed, inject, Injectable, signal } from '@angular/core';
import { forkJoin, map, Observable, tap } from 'rxjs';
import { Empeno } from '../interfaces/Empeno_Interface';
import { ProductoService } from './Producto.service';

@Injectable({
  providedIn: 'root',
})
export class EmpenoService {
  productoService = inject(ProductoService);
  private apiUrl = 'http://localhost:8080/api/api/empenos';

  // Estado global reactivo
  empenos = signal<Empeno[]>([]);

  constructor(private http: HttpClient) {
    this.inicializarData();
  }

  /* CRUD */

  // Obtener todos los empeños
  getAll(): Observable<Empeno[]> {
    return this.http.get<Empeno[]>(this.apiUrl).pipe(tap((data) => this.guardarLocal(data)));
  }

  // Obtener un empeño por ID
  getById(id: number): Observable<Empeno> {
    return this.http.get<Empeno>(`${this.apiUrl}/${id}`);
  }

  // Crear un empeño
  create(empeno: Partial<Empeno>): Observable<Empeno> {
    return this.http.post<Empeno>(this.apiUrl, empeno).pipe(tap(() => this.cargarEmpenos()));
  }

  // Actualizar un empeño
  update(id: number, empeno: Empeno): Observable<Empeno> {
    return this.http
      .put<Empeno>(`${this.apiUrl}/${id}`, empeno)
      .pipe(tap(() => this.cargarEmpenos()));
  }

  // Eliminar empeño
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(tap(() => this.eliminarLocal(id)));
  }

  /* Funciones + Mapeo */
  // Producto en Empeño
  getProductoOfEmpeno() {
    return forkJoin({
      empenos: this.getAll(),
      productos: this.productoService.getAll(),
    }).pipe(
      map(({ empenos, productos }) =>
        empenos.map((e) => ({
          ...e,
          productoNombre: productos.find((p) => p.id === e.producto)?.nombre,
        }))
      )
    );
  }

  // Total de empeños
  totalEmpenos = computed(() => this.empenos().length);

  // Total del monto de todos los empeños
  totalMonto = computed(() => this.empenos().reduce((acc, e) => acc + e.valorRecuperacion, 0));

  // Obtener solo los empeños activos
  empenosActivos = computed(() => this.empenos().filter((e) => e.estado === 1));

  //  LOCAL STORAGE + ESTADO

  private inicializarData() {
    const data = localStorage.getItem('empenos');
    if (data) {
      this.empenos.set(JSON.parse(data));
      console.log('Empenos cargados desde localStorage');
    }

    this.cargarEmpenos();
  }

  private guardarLocal(data: Empeno[]) {
    this.empenos.set(data);
    localStorage.setItem('empenos', JSON.stringify(data));
  }

  private cargarEmpenos() {
    this.getAll().subscribe();
  }

  private eliminarLocal(id: number) {
    const updated = this.empenos().filter((e) => e.id !== id);
    this.guardarLocal(updated);
  }

  eliminarLocalStorage(id: number) {
    this.eliminarLocal(id);
  }
}
