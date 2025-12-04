import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Tranferencia } from 'src/app/shared/interfaces/Transferencia-Interface';
import { ModalTransferenciaAdministrador } from '../../../services/mmodalTransferencia.service';

@Component({
  selector: 'app-add-transfer-admin',
  imports: [ReactiveFormsModule],
  templateUrl: './add-transfer-admin.html',
  styleUrl: './add-transfer-admin.css',
})
export class AddTransferAdmin {
  @Input() modo: 'agregar' | 'editar' = 'agregar';
  @Input() set transferencia(value: Partial<Tranferencia> | null) {
    if (value) {
      this._usuario = value;
      this.formAddTransferencia.patchValue(value);
    }
  }
  private _usuario: Partial<Tranferencia> | null = null;

  // Salida (evento al padre)
  @Output() guardar = new EventEmitter<Partial<Tranferencia>>();

  // Servicios
  private fb = inject(FormBuilder);
  modalState = inject(ModalTransferenciaAdministrador);

  // Formulario reactivo
  formAddTransferencia: FormGroup = this.fb.group({});

  // Enviar datos al componente padre
  onSubmit(): void {
    if (this.formAddTransferencia.invalid) {
      this.formAddTransferencia.markAllAsTouched();
      return;
    }

    const datosFormulario = this.formAddTransferencia.value;

    // Emitir datos combinados
    const datosEmitidos: Partial<Tranferencia> = {
      ...this._usuario,
      ...datosFormulario,
    };

    this.guardar.emit(datosEmitidos);
  }
  // Cerrar modal
  cerrarModal(): void {
    this.modalState.close();
  }
}
