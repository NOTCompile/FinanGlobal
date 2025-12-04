import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Tipo_Transferencia } from 'src/app/shared/interfaces/Tipo_Transferencia-Interface';
import { ModalTTransfrenciasAdministrador } from '../../../services/modalTTransferencias.service';
import { Tipo_Documento } from 'src/app/shared/interfaces/Tipo_Documento-Interface';

@Component({
  selector: 'app-add-transferencia-admin',
  imports: [ReactiveFormsModule],
  templateUrl: './add-ttransferencia-admin.html',
  styleUrl: './add-ttransferencia-admin.css',
})
export class AddTtransferenciaAdmin {
  // Entradas
  @Input() modo: 'agregar' | 'editar' = 'agregar';
  @Input() set tTransferencia(value: Partial<Tipo_Transferencia> | null) {
    if (value) {
      this._tTransferencia = value;
      this.formAddtTransferencia.patchValue(value);
    }
  }
  private _tTransferencia: Partial<Tipo_Transferencia> | null = null;

  // Salida
  @Output() guardar = new EventEmitter<Partial<Tipo_Transferencia>>();

  // Servicios
  private fb = inject(FormBuilder);
  modalState = inject(ModalTTransfrenciasAdministrador);

  // Formulario reactivo
  formAddtTransferencia: FormGroup = this.fb.group({
    nombre: ['', Validators.required],
    descripcion: ['', Validators.required],
  });

  // Cerrar modal
  cerrarModal(): void {
    this.modalState.close();
  }

  // Enviar datos al componente padre
  onSubmit(): void {
    if (this.formAddtTransferencia.invalid) {
      this.formAddtTransferencia.markAllAsTouched();
      return;
    }

    const datosFormulario = this.formAddtTransferencia.value;

    // Emitir datos combinados
    const datosEmitidos: Partial<Tipo_Transferencia> = {
      ...this._tTransferencia,
      ...datosFormulario,
    };

    this.guardar.emit(datosEmitidos);
  }
}
