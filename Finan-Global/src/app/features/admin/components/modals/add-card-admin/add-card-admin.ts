import { Component, EventEmitter, inject, Input, OnInit, Output, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ModalTarjetaAdministrador } from '../../../services/modalTarjeta.service';
import { TarjetaDTO } from 'src/app/shared/interfaces/DTO/TarjetaDTO-Interface';
import { Cuenta_Bancaria } from 'src/app/shared/interfaces/Cuenta_Bancaria-Interface';
import { CuentaBancariaService } from 'src/app/shared/services/CuentaBancaria.service';
import { Tipo_Tarjeta } from 'src/app/shared/interfaces/Tipo_Tarjeta-Interface';
import { TipoTarjetaService } from 'src/app/shared/services/TipoTarjeta.service';
import { Tarjeta } from 'src/app/shared/interfaces/Tarjetas-Interface';

@Component({
  selector: 'app-add-card-admin',
  imports: [ReactiveFormsModule],
  templateUrl: './add-card-admin.html',
  styleUrl: './add-card-admin.css',
})
export class AddCardAdmin implements OnInit {
  // Servicios
  private fb = inject(FormBuilder);
  modalEstado = inject(ModalTarjetaAdministrador);
  private cuentaBancariaServicio = inject(CuentaBancariaService);
  private tipoTarjetaServicio = inject(TipoTarjetaService);

  //Entrada
  @Input() modo: 'agregar' | 'editar' = 'agregar';
  @Input() set tarjetaBancaria(value: Partial<Tarjeta> | null) {
    if (value) {
      this._tarjeta = value;

      this.formAddTarjeta.patchValue({
        cuentaBancaria: value.cuentaBancaria?.id ?? '',
        tipoTarjeta: value.tipoTarjeta?.id ?? '',
        fechaCaducidad: value.fechaCaducidad
          ? new Date(value.fechaCaducidad).toISOString().substring(0, 10)
          : '',
      });
    }
  }
  private _tarjeta: Partial<Tarjeta> | null = null;

  //Salida
  @Output() guardar = new EventEmitter<TarjetaDTO>();

  //Estado
  cuentasBancarias = signal<Cuenta_Bancaria[]>([]);
  tipoTarjetas = signal<Tipo_Tarjeta[]>([]);

  ngOnInit(): void {
    this.obtenerCuentasBancarias();
    this.obtenerTipoTarjeta();
  }

  //Formulario
  formAddTarjeta: FormGroup = this.fb.group({
    cuentaBancaria: ['', Validators.required],
    tipoTarjeta: ['', Validators.required],
    fechaCaducidad: ['', Validators.required],
  });

  // Obtener Cuentas
  obtenerCuentasBancarias(): void {
    this.cuentaBancariaServicio.getAll().subscribe({
      next: (data) => {
        this.cuentasBancarias.set(data);
      },
      error: (err) => console.error('Error al obtener cuentas:', err),
    });
  }

  obtenerTipoTarjeta(): void {
    this.tipoTarjetaServicio.getAll().subscribe({
      next: (data) => {
        this.tipoTarjetas.set(data);
      },
      error: (err) => console.error('Error al obtener cuentas:', err),
    });
  }

  // Enviar datos al componente padre
  onSubmit(): void {
    if (this.formAddTarjeta.invalid) {
      this.formAddTarjeta.markAllAsTouched();
      return;
    }

    const datos = this.formAddTarjeta.value;

    const dto: TarjetaDTO = {
      id: this._tarjeta?.id ?? 0,
      codigo: this.modo === 'agregar' ? this.generarCodigoBancario() : this._tarjeta!.codigo!,
      cvc: this.modo === 'agregar' ? this.generarCVC() : this._tarjeta!.cvc!,
      fechaCaducidad: datos.fechaCaducidad.toString(), // <- string correcto
      cuentaBancaria: { id: Number(datos.cuentaBancaria) },
      tipoTarjeta: { id: Number(datos.tipoTarjeta) },
    };

    console.log('Datos emitidos al padre:', dto);
    this.guardar.emit(dto);
  }

  private generarCodigoBancario(): string {
    return Math.floor(1000000000000000 + Math.random() * 9000000000000000).toString();
  }

  private generarCVC(): string {
    return Math.floor(100 + Math.random() * 999).toString();
  }

  cerrarModal(): void {
    this.modalEstado.close();
  }
}
