import { Component, inject, OnInit, signal } from '@angular/core';
import { Tipo_Documento } from 'src/app/shared/interfaces/Tipo_Documento-Interface';
import { Tipo_Producto } from 'src/app/shared/interfaces/Tipo_Producto-Interface';
import { Tipo_Transferencia } from 'src/app/shared/interfaces/Tipo_Transferencia-Interface';
import { TipoDocumentoService } from 'src/app/shared/services/TipoDocumento.service';
import { TipoProductoService } from 'src/app/shared/services/TipoProducto.service';
import { TipoTransferenciaService } from 'src/app/shared/services/TipoTransferencia.service';
import { ModalTDocumentoAdministrador } from '../../services/modalTDocumento.service';
import { ModalTProductsAdministrador } from '../../services/modalTProducts.service';
import { ModalTTransfrenciasAdministrador } from '../../services/modalTTransferencias.service';
import { AddTdocumentsAdmin } from '../../components/modals/add-tdocuments-admin/add-tdocuments-admin';
import { AddTproductsAdmin } from '../../components/modals/add-tproducts-admin/add-tproducts-admin';
import { AddTtransferenciaAdmin } from '../../components/modals/add-ttransferencia-admin/add-ttransferencia-admin';
import { Tipo_DocumentoDTO } from 'src/app/shared/interfaces/DTO/Tipo_DocumentoDTO-Interface';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-utils-page',
  imports: [AddTdocumentsAdmin, AddTproductsAdmin, AddTtransferenciaAdmin],
  providers: [
    ModalTDocumentoAdministrador,
    ModalTProductsAdministrador,
    ModalTTransfrenciasAdministrador,
  ],
  templateUrl: './utils-page.component.html',
  styleUrl: './utils-page.component.css',
})
export default class UtilsAdmin implements OnInit {
  // Servicios
  private tipoDocumentoService = inject(TipoDocumentoService);
  private tipoProductoService = inject(TipoProductoService);
  private tipoTranferenciaService = inject(TipoTransferenciaService);

  private modalDocumentoService = inject(ModalTDocumentoAdministrador);
  private modalProductoService = inject(ModalTProductsAdministrador);
  private modalTransferenciaService = inject(ModalTTransfrenciasAdministrador);

  // Estado
  tipoDocumento = this.tipoDocumentoService.tipoDocumentos;
  tipoProducto = this.tipoProductoService.tipoProductos;
  tipoTransferencia = this.tipoTranferenciaService.tipoTransferencias;

  modo = signal<'agregar' | 'editar'>('agregar');
  // Editar Modal
  tipoDocumentoSeleccionado = signal<Partial<Tipo_DocumentoDTO> | null>(null);
  tipoProductoSeleccionado = signal<Partial<Tipo_Producto> | null>(null);
  tipoTransferenciaSeleccionado = signal<Partial<Tipo_Transferencia> | null>(null);

  ngOnInit(): void {
    this.cargarTipoDocumentos();
    this.cargarTipoProductos();
    this.cargarTipoTransferencias();
  }

  /* Cargar Datos */
  // Cargar Tipo Documentos
  cargarTipoDocumentos(): void {
    this.tipoDocumentoService.getAll().subscribe({
      next: () => {
        console.log('Tipo Documentos cargado');
        this.ordenarCuentasTD();
      },
      error: (err) => console.error('Error al cargar tipo documentos:', err),
    });
  }

  // Cargar Tipo Productos
  cargarTipoProductos(): void {
    this.tipoProductoService.getAll().subscribe({
      next: () => {
        console.log('Tipo Productos cargado');
        this.ordenarCuentasTP();
      },
      error: (err) => console.error('Error al cargar tipo documentos:', err),
    });
  }

  // Cargar Tipo Transferencias
  cargarTipoTransferencias(): void {
    this.tipoTranferenciaService.getAll().subscribe({
      next: () => {
        console.log('Tipo Transferencia cargado');
        this.ordenarCuentasTT();
      },
      error: (err) => console.error('Error al cargar tipo documentos:', err),
    });
  }

  /* Abrir Modal */
  abrirTDocumentos(): void {
    this.modo.set('agregar');
    this.tipoDocumentoSeleccionado.set({
      nombre: '',
      descripcion: '',
    });
    this.modalDocumentoService.open();
  }

  abrirTProductos(): void {
    this.modo.set('agregar');
    this.tipoProductoSeleccionado.set({
      nombre: '',
      descripcion: '',
    });
    this.modalProductoService.open();
  }

  abrirTTransferencias(): void {
    this.modo.set('agregar');
    this.tipoTransferenciaSeleccionado.set({
      nombre: '',
      descripcion: '',
    });
    this.modalTransferenciaService.open();
  }

  /* EditarDatos */
  editarTDocumentos(documento: Tipo_Documento): void {
    this.modo.set('editar');
    this.tipoDocumentoSeleccionado.set({
      id: documento.id,
      nombre: documento.tipo,
      descripcion: documento.descripcion,
    });
    this.modalDocumentoService.open();
  }

  editarTProductos(tproducto: Tipo_Producto): void {
    this.modo.set('editar');
    this.tipoProductoSeleccionado.set(tproducto);
    this.modalProductoService.open();
  }

  editarTTransferencias(ttransferencias: Tipo_Transferencia): void {
    this.modo.set('editar');
    this.tipoTransferenciaSeleccionado.set(ttransferencias);
    this.modalTransferenciaService.open();
  }

  /* Guardar Datos */
  onGuardarTDocumentos(data: Partial<Tipo_DocumentoDTO>): void {
    const modo = this.modo();

    if (modo === 'agregar') {
      // Crear NO incluye id
      const dtoCrear: Tipo_DocumentoDTO = {
        id: 0,
        nombre: data.nombre!,
        descripcion: data.descripcion!,
      };

      this.tipoDocumentoService.create(dtoCrear).subscribe({
        next: () => {
          this.cargarTipoDocumentos();
          this.modalDocumentoService.close();
          this.ordenarCuentasTD();
        },
        error: (err) => console.error(err),
      });
    } else {
      // EDITAR
      const dtoEditar: Tipo_DocumentoDTO = {
        id: data.id!,
        nombre: data.nombre!,
        descripcion: data.descripcion!,
      };

      this.tipoDocumentoService.update(dtoEditar.id, dtoEditar).subscribe({
        next: () => {
          alert('Tipo de documento actualizado correctamente');
          this.cargarTipoDocumentos();
          this.modalDocumentoService.close();
          this.ordenarCuentasTD();
        },
        error: (err) => console.error(err),
      });
    }
  }

  onGuardarTProductos(data: Partial<Tipo_Producto>): void {
    const modoActual = this.modo(); // 'agregar' o 'editar'

    // Validación mínima
    if (!data.nombre || !data.descripcion) {
      alert('Nombre y descripción son obligatorios');
      return;
    }

    if (modoActual === 'agregar') {
      // Crear nuevo tipo de producto → omitimos id porque lo genera el backend
      const nuevoTipo: Omit<Tipo_Producto, 'id'> = {
        nombre: data.nombre,
        descripcion: data.descripcion,
      };

      this.tipoProductoService.create(nuevoTipo as Tipo_Producto).subscribe({
        next: () => {
          alert('Tipo de producto creado correctamente');
          this.cargarTipoProductos(); // recarga la lista
          this.modalProductoService.close();
          this.ordenarCuentasTP();
        },
        error: (err) => console.error('Error al crear tipo de producto:', err),
      });
    } else {
      // Editar tipo de producto → id obligatorio
      const seleccionado = this.tipoProductoSeleccionado();
      if (!seleccionado?.id) {
        console.error('No se encontró el ID del producto a editar');
        return;
      }

      const tipoActualizado: Tipo_Producto = {
        id: seleccionado.id,
        nombre: data.nombre,
        descripcion: data.descripcion,
      };

      this.tipoProductoService.update(tipoActualizado.id, tipoActualizado).subscribe({
        next: () => {
          alert('Tipo de producto actualizado correctamente');
          this.cargarTipoProductos();
          this.modalProductoService.close();
          this.ordenarCuentasTP();
        },
        error: (err) => console.error('Error al actualizar tipo de producto:', err),
      });
    }
  }

  onGuardarTTransferencia(data: Partial<Tipo_Transferencia>) {
    const modoActual = this.modo(); // 'agregar' o 'editar'

    // Validación mínima
    if (!data.nombre || !data.descripcion) {
      alert('Nombre y descripción son obligatorios');
      return;
    }

    if (modoActual === 'agregar') {
      // Crear nuevo tipo de producto → omitimos id porque lo genera el backend
      const nuevoTipo: Omit<Tipo_Transferencia, 'id'> = {
        nombre: data.nombre,
        descripcion: data.descripcion,
      };

      this.tipoTranferenciaService.create(nuevoTipo as Tipo_Transferencia).subscribe({
        next: () => {
          alert('Tipo de producto creado correctamente');
          this.cargarTipoTransferencias(); // recarga la lista
          this.modalTransferenciaService.close();
          this.ordenarCuentasTT();
        },
        error: (err) => console.error('Error al crear tipo de producto:', err),
      });
    } else {
      // Editar tipo de producto → id obligatorio
      const seleccionado = this.tipoTransferenciaSeleccionado();
      if (!seleccionado?.id) {
        console.error('No se encontró el ID de transferencia a editar');
        return;
      }

      const tipoActualizado: Tipo_Transferencia = {
        id: seleccionado.id,
        nombre: data.nombre,
        descripcion: data.descripcion,
      };

      this.tipoTranferenciaService.update(tipoActualizado.id, tipoActualizado).subscribe({
        next: () => {
          alert('Tipo de producto actualizado correctamente');
          this.cargarTipoTransferencias();
          this.modalTransferenciaService.close();
          this.ordenarCuentasTT();
        },
        error: (err) => console.error('Error al actualizar tipo de producto:', err),
      });
    }
  }

  /* Eliminar Datos */
  onEliminarTDocumento(id: number): void {
    if (!confirm('¿Deseas eliminar este Tipo de Documento?')) return;

    this.tipoDocumentoService.delete(id).subscribe({
      next: () => {
        alert('Tipo de Documento eliminado correctamente');
        this.ordenarCuentasTD();
        // No es necesario recargar, la señal ya se actualizó en el servicio
      },
      error: (err) => console.error('Error al eliminar Tipo de Documento:', err),
    });
  }

  onEliminarTProducto(id: number): void {
    if (!confirm('¿Deseas eliminar este Tipo de Producto?')) return;

    this.tipoProductoService.delete(id).subscribe({
      next: () => {
        alert('Tipo de Producto eliminado correctamente');
        this.ordenarCuentasTP();
        // No es necesario recargar, la señal ya se actualizó en el servicio
      },
      error: (err) => console.error('Error al eliminar Tipo de Producto:', err),
    });
  }

  onEliminarTTransferencia(id: number): void {
    if (!confirm('¿Deseas eliminar este Tipo de Transferencia?')) return;

    this.tipoTranferenciaService.delete(id).subscribe({
      next: () => {
        alert('Tipo de Transferencia eliminado correctamente');
        // No es necesario recargar, la señal ya se actualizó en el servicio
      },
      error: (err) => console.error('Error al eliminar Tipo de Transferencia:', err),
    });
  }

  // Ordenar
  private ordenarCuentasTD(): void {
    const ordenadas = [...this.tipoDocumentoService.tipoDocumentos()].sort((a, b) => a.id - b.id);
    this.tipoDocumentoService.tipoDocumentos.set(ordenadas);
  }

  private ordenarCuentasTP(): void {
    const ordenadas = [...this.tipoProductoService.tipoProductos()].sort((a, b) => a.id - b.id);
    this.tipoProductoService.tipoProductos.set(ordenadas);
  }

  private ordenarCuentasTT(): void {
    const ordenadas = [...this.tipoTranferenciaService.tipoTransferencias()].sort(
      (a, b) => a.id - b.id
    );
    this.tipoTranferenciaService.tipoTransferencias.set(ordenadas);
  }
}
