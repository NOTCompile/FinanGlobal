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
import { Cuenta_BancariaDTO } from 'src/app/shared/interfaces/DTO/Cuenta_BancariaDTO-Interface';

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
  @Input() set cuentaBancaria(value: Partial<Cuenta_BancariaDTO> | null) {
    if (value) {
      this._cuenta = value;
      this.formAddCuenta.patchValue({
        nombre: value.nombre,
        usuario: value.id_usuario,
        saldo: value.saldo,
      });
    }
  }
  private _cuenta: Partial<Cuenta_BancariaDTO> | null = null;

  // Salida
  @Output() guardar = new EventEmitter<Partial<Cuenta_BancariaDTO>>();

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
    saldo: ['', Validators.required],
  });

  // Enviar datos al componente padre
  onSubmit(): void {
    if (this.formAddCuenta.invalid) {
      this.formAddCuenta.markAllAsTouched();
      return;
    }

    const datosFormulario = this.formAddCuenta.value;

    // Emitir datos combinados
    const datosEmitidos: Partial<Cuenta_BancariaDTO> = {
      id: this._cuenta?.id ?? 0,
      nombre: datosFormulario.nombre,
      numero_cuenta:
        this.modo === 'agregar' ? this.generarNumeroCuenta() : this._cuenta?.numero_cuenta!,
      n_intercuenta:
        this.modo === 'agregar' ? this.generarNumeroInterCuenta() : this._cuenta?.n_intercuenta!,
      saldo: datosFormulario.saldo,
      id_usuario: datosFormulario.usuario,
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
