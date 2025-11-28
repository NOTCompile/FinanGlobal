import { Injectable, signal } from '@angular/core';
import { Producto } from '../interfaces/Producto-Interface';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ProductoService {
  private apiUrl = 'http://localhost:8080/api/api/productos';

  productos = signal<Producto[]>([]);

  constructor(private http: HttpClient) {
    this.inicializarData();
  }

  // Obtener todas los tipos
  getAll(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.apiUrl).pipe(tap((data) => this.guardarLocal(data)));
  }

  /* Local Storage */
  private inicializarData() {
    const data = localStorage.getItem('Productos');
    if (data) {
      this.productos.set(JSON.parse(data));
      console.log('Productos cargado desde localStorage');
    }
    this.cargarTipoDocumentos();
  }

  private cargarTipoDocumentos() {
    this.getAll().subscribe();
  }

  // Guardar en Local Storage
  private guardarLocal(data: Producto[]) {
    this.productos.set(data);
    localStorage.setItem('Productos', JSON.stringify(data));
  }
}
