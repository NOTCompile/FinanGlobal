import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Tipo_Producto } from 'src/app/shared/interfaces/Tipo_Producto-Interface';
import { ModalTProductsAdministrador } from '../../../services/modalTProducts.service';

@Component({
  selector: 'app-add-tproducts-admin',
  imports: [ReactiveFormsModule],
  templateUrl: './add-tproducts-admin.html',
  styleUrl: './add-tproducts-admin.css',
})
export class AddTproductsAdmin {
  // Entradas
  @Input() modo: 'agregar' | 'editar' = 'agregar';
  @Input() set tProducto(value: Partial<Tipo_Producto> | null) {
    if (value) {
      this._tProducto = value;
      this.formAddtProducto.patchValue(value);
    }
  }
  private _tProducto: Partial<Tipo_Producto> | null = null;

  // Salida
  @Output() guardar = new EventEmitter<Partial<Tipo_Producto>>();

  // Servicios
  private fb = inject(FormBuilder);
  modalState = inject(ModalTProductsAdministrador);

  // Formulario reactivo
  formAddtProducto: FormGroup = this.fb.group({
    nombre: ['', Validators.required],
    descripcion: ['', Validators.required],
  });

  // Cerrar modal
  cerrarModal(): void {
    this.modalState.close();
  }

  // Enviar datos al componente padre
  onSubmit(): void {
    if (this.formAddtProducto.invalid) {
      this.formAddtProducto.markAllAsTouched();
      return;
    }

    const datosFormulario = this.formAddtProducto.value;

    // Emitir datos combinados
    const datosEmitidos: Partial<Tipo_Producto> = {
      ...this._tProducto,
      ...datosFormulario,
    };

    this.guardar.emit(datosEmitidos);
  }
}
