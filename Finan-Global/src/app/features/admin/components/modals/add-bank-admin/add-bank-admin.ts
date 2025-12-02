import {
  Component,
  effect,
  EventEmitter,
  inject,
  Input,
  OnInit,
  Output,
  signal,
} from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ModalCuentaBancariaAdministrador } from '../../../services/modalCuentaBancaria.service';
import { Cuenta_Bancaria } from 'src/app/shared/interfaces/Cuenta_Bancaria-Interface';
import { usuarioService } from 'src/app/shared/services/Usuario.service';
import { Usuario } from 'src/app/shared/interfaces/Usuario-Interface';

@Component({
  selector: 'app-add-bank-admin',
  imports: [ReactiveFormsModule],
  templateUrl: './add-bank-admin.html',
  styleUrl: './add-bank-admin.css',
})
export class AddBankAdmin implements OnInit {
  // Servicios
  private fb = inject(FormBuilder);
  modalEstado = inject(ModalCuentaBancariaAdministrador);
  private usuarioServicio = inject(usuarioService);

  // Entradas
  @Input() modo: 'agregar' | 'editar' = 'agregar';
  @Input() set cuentaBancaria(value: Partial<Cuenta_Bancaria> | null) {
    if (value) {
      this._cuenta = value;
      this.formAddCuenta.patchValue(value);
    }
  }
  private _cuenta: Partial<Cuenta_Bancaria> | null = null;

  // Salida
  @Output() guardar = new EventEmitter<Partial<Cuenta_Bancaria>>();

  // Estados
  usuarios = signal<Usuario[]>([]);

  constructor() {
    effect(() => {
      if (this.modo === 'agregar') {
        this.formAddCuenta.reset({
          sexo: 'Seleccione...',
        });
      } else if (this.modo === 'editar' && this._cuenta) {
        this.formAddCuenta.patchValue(this._cuenta);
      }
    });
  }

  ngOnInit(): void {
    this.obtenerUsuarios();
  }

  // Obtener clientes SELECT
  obtenerUsuarios(): void {
    this.usuarioServicio.findByRol(4).subscribe({
      next: (data) => {
        this.usuarios.set(data);
      },
      error: (err) => console.error('Error al obtener usuarios:', err),
    });
  }

  // Formulario
  formAddCuenta: FormGroup = this.fb.group({
    nombre: ['', [Validators.required]],
    usuario: ['', Validators.required],
  });

  // Enviar datos al componente padre
  onSubmit(): void {
    if (this.formAddCuenta.invalid) {
      this.formAddCuenta.markAllAsTouched();
      return;
    }

    const datosFormulario = this.formAddCuenta.value;

    // Generar números aleatorios para las cuentas
    const n_intercuenta = this.generarNumeroInterCuenta();
    const ncuenta = this.generarNumeroCuenta();

    // Emitir datos combinados
    const datosEmitidos: Partial<Cuenta_Bancaria> = {
      ...this._cuenta,
      ...datosFormulario,
      n_intercuenta,
      ncuenta,
      saldo: 0,
    };

    console.log('Datos de cuenta nueva', datosEmitidos);

    this.guardar.emit(datosEmitidos);
  }

  // Funciones de N° Aleatorios
  private generarNumeroInterCuenta(): string {
    return Math.floor(1000000000000000 + Math.random() * 900000000000000).toString();
  }

  private generarNumeroCuenta(): string {
    return Math.floor(10000000000000000000 + Math.random() * 90000000000000000000).toString();
  }

  cerrarModal(): void {
    this.modalEstado.close();
  }
}
