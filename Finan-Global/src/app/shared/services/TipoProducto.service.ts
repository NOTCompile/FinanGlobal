import { Injectable, signal } from '@angular/core';
import { Tipo_Producto } from '../interfaces/Tipo_Producto-Interface';
import { Observable, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class TipoProductoService {
  private apiUrl = 'http://localhost:8080/api/api/tipos-producto';

  tipoProductos = signal<Tipo_Producto[]>([]);

  constructor(private http: HttpClient) {
    this.inicializarData();
  }

  // Obtener todas los tipos
  getAll(): Observable<Tipo_Producto[]> {
    return this.http
      .get<Tipo_Producto[]>(this.apiUrl)
      .pipe(tap((data) => this.guardarLocal(data)));
  }

  /* Local Storage */
  private inicializarData() {
    const data = localStorage.getItem('tipoDocumentos');
    if (data) {
      this.tipoProductos.set(JSON.parse(data));
      console.log('tipoDocumentos cargado desde localStorage');
    }
    this.cargarTipoDocumentos();
  }

  private cargarTipoDocumentos() {
    this.getAll().subscribe();
  }

  // Guardar en Local Storage
  private guardarLocal(data: Tipo_Producto[]) {
    this.tipoProductos.set(data);
    localStorage.setItem('tipoDocumentos', JSON.stringify(data));
  }
}
