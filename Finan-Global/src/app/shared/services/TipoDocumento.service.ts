import { Injectable, signal } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { Tipo_Documento } from '../interfaces/Tipo_Documento-Interface';
import { HttpClient } from '@angular/common/http';
import { Tipo_DocumentoDTO } from '../interfaces/DTO/Tipo_DocumentoDTO-Interface';

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

  getById(id: number): Observable<Tipo_Documento> {
    return this.http.get<Tipo_Documento>(`${this.apiUrl}/${id}`);
  }

  create(dto: Tipo_DocumentoDTO): Observable<Tipo_Documento> {
    // Convertimos DTO → entidad del backend
    const body: Tipo_Documento = {
      id: dto.id,
      tipo: dto.nombre, // MAPEADO
      descripcion: dto.descripcion,
    };

    return this.http.post<Tipo_Documento>(this.apiUrl, body).pipe(
      tap((nuevo) => {
        const lista = [...this.tipoDocumentos(), nuevo];
        this.guardarLocal(lista);
      })
    );
  }

  update(id: number, dto: Tipo_DocumentoDTO): Observable<Tipo_Documento> {
    const body: Tipo_Documento = {
      id: dto.id,
      tipo: dto.nombre,
      descripcion: dto.descripcion,
    };

    return this.http.put<Tipo_Documento>(`${this.apiUrl}/${id}`, body).pipe(
      tap((actualizado) => {
        const lista = this.tipoDocumentos().map((td) => (td.id === id ? actualizado : td));
        this.guardarLocal(lista);
      })
    );
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      tap(() => {
        const actualizada = this.tipoDocumentos().filter((td) => td.id !== id);
        this.guardarLocal(actualizada);
      })
    );
  }

  /* Local Storage */
  private inicializarData() {
    const data = localStorage.getItem('tipoDocumentos');
    if (data) {
      this.tipoDocumentos.set(JSON.parse(data));
      console.log('tipoDocumentos cargado desde localStorage');
    }
    this.cargarTipoDocumentos();
  }

  private cargarTipoDocumentos() {
    this.getAll().subscribe();
  }

  // Guardar en Local Storage
  private guardarLocal(data: Tipo_Documento[]) {
    this.tipoDocumentos.set(data);
    localStorage.setItem('tipoDocumentos', JSON.stringify(data));
  }
}
