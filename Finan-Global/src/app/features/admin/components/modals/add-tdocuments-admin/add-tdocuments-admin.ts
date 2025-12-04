import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Tipo_Documento } from 'src/app/shared/interfaces/Tipo_Documento-Interface';
import { ModalTDocumentoAdministrador } from '../../../services/modalTDocumento.service';
import { Tipo_DocumentoDTO } from 'src/app/shared/interfaces/DTO/Tipo_DocumentoDTO-Interface';

@Component({
  selector: 'app-add-tdocuments-admin',
  imports: [ReactiveFormsModule],
  templateUrl: './add-tdocuments-admin.html',
  styleUrl: './add-tdocuments-admin.css',
})
export class AddTdocumentsAdmin {
  // Entradas
  @Input() modo: 'agregar' | 'editar' = 'agregar';
  @Input() set tDocumento(value: Partial<Tipo_DocumentoDTO> | null) {
    if (value) {
      this._tDocumento = value;
      this.formAddtDocumento.patchValue({
        nombre: value.nombre,
        descripcion: value.descripcion,
      });
    }
  }
  private _tDocumento: Partial<Tipo_DocumentoDTO> | null = null;

  // Salida (evento al padre)
  @Output() guardar = new EventEmitter<Partial<Tipo_DocumentoDTO>>();

  //Servicios
  private fb = inject(FormBuilder);
  modalState = inject(ModalTDocumentoAdministrador);

  // Formulario reactivo
  formAddtDocumento: FormGroup = this.fb.group({
    nombre: ['', Validators.required],
    descripcion: ['', Validators.required],
  });

  // Enviar datos al componente padre
  onSubmit(): void {
    if (this.formAddtDocumento.invalid) {
      this.formAddtDocumento.markAllAsTouched();
      return;
    }

    const valores = this.formAddtDocumento.value;

    const dto: Partial<Tipo_DocumentoDTO> = {
      id: this._tDocumento?.id ?? 0,
      nombre: valores.nombre,
      descripcion: valores.descripcion,
    };

    this.guardar.emit(dto);
  }

  // Cerrar modal
  cerrarModal(): void {
    this.modalState.close();
  }
}
