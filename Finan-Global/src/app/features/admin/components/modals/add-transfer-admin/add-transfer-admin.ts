import { Component, EventEmitter, inject, Input, OnInit, Output, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Tranferencia } from 'src/app/shared/interfaces/Transferencia-Interface';
import { ModalTransferenciaAdministrador } from '../../../services/mmodalTransferencia.service';
import { TranferenciaDTO } from 'src/app/shared/interfaces/DTO/TransferenciaDTO-Interface';
import { Cuenta_Bancaria } from 'src/app/shared/interfaces/Cuenta_Bancaria-Interface';
import { CuentaBancariaService } from 'src/app/shared/services/CuentaBancaria.service';

@Component({
  selector: 'app-add-transfer-admin',
  imports: [ReactiveFormsModule],
  templateUrl: './add-transfer-admin.html',
  styleUrl: './add-transfer-admin.css',
})
export class AddTransferAdmin implements OnInit {
  @Input() modo: 'agregar' | 'editar' = 'agregar';
  @Input() set transferencia(value: Partial<TranferenciaDTO> | null) {
    if (value) {
      this._transferencia = value;
      this.formAddTransferencia.patchValue({
        monto: value.monto,
        nCuentaEmisora: value.nCuentaEmisora,
        nCuentaReceptora: value.nCuentaReceptora,
        idTipoTransferencia: value.idTipoTransferencia,
      });
    }
  }
  private _transferencia: Partial<TranferenciaDTO> | null = null;

  // Salida (evento al padre)
  @Output() guardar = new EventEmitter<Partial<TranferenciaDTO>>();

  // Estados
  cuentaBancaria = signal<Cuenta_Bancaria[]>([]);

  // Servicios
  private fb = inject(FormBuilder);
  modalState = inject(ModalTransferenciaAdministrador);
  cuentaBancariaServicio = inject(CuentaBancariaService);

  ngOnInit(): void {
    this.obtenerCuentasBancarias();
  }

  // Obtener Cuentas Bancarias
  obtenerCuentasBancarias(): void {
    this.cuentaBancariaServicio.getAll().subscribe({
      next: (data) => {
        this.cuentaBancaria.set(data);
      },
      error: (err) => console.error('Error al obtener cuentas bancarias:', err),
    });
  }

  // Formulario reactivo
  formAddTransferencia: FormGroup = this.fb.group({
    monto: ['', Validators.required],
    nCuentaEmisora: ['', Validators.required],
    nCuentaReceptora: ['', Validators.required],
    idTipoTransferencia: ['', Validators.required],
  });

  // Enviar datos al componente padre
  onSubmit(): void {
    if (this.formAddTransferencia.invalid) {
      this.formAddTransferencia.markAllAsTouched();
      return;
    }

    const datosFormulario = this.formAddTransferencia.value;

    // Emitir datos combinados
    const datosEmitidos: Partial<TranferenciaDTO> = {
      monto: datosFormulario.monto,
      nCuentaEmisora: datosFormulario.nCuentaEmisora,
      nCuentaReceptora: datosFormulario.nCuentaReceptora,
      idTipoTransferencia: datosFormulario.idTipoTransferencia,
    };

    this.guardar.emit(datosEmitidos);
  }

  // Cerrar modal
  cerrarModal(): void {
    this.modalState.close();
  }
}
