import { Injectable, signal } from '@angular/core';
import { Observable, single, tap } from 'rxjs';
import { Tipo_Documento } from '../interfaces/Tipo_Documento-Interface';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class TipoDocumentoService {
  private apiUrl = 'http://localhost:8080/api/api/tipos-documento';

  tipoDocumentos = signal<Tipo_Documento[]>([]);

  constructor(private http: HttpClient) {
    this.inicializarData();
  }

  // Obtener todas los tipos
  getAll(): Observable<Tipo_Documento[]> {
    return this.http
      .get<Tipo_Documento[]>(this.apiUrl)
      .pipe(tap((data) => this.guardarLocal(data)));
  }


  /* Local Storage */
  private inicializarData() {
    const data = localStorage.getItem('tipoDocumentos');
    if (data) {
      this.tipoDocumentos.set(JSON.parse(data));
      console.log('tipoDocumentos cargado desde localStorage')
    }
    this.cargarTipoDocumentos();
  }

  private cargarTipoDocumentos(){
    this.getAll().subscribe();
  }

  // Guardar en Local Storage
  private guardarLocal(data: Tipo_Documento[]) {
    this.tipoDocumentos.set(data);
    localStorage.setItem('tipoDocumentos', JSON.stringify(data));
  }
}
