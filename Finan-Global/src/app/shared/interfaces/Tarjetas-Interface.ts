import { Cuenta_Bancaria } from './Cuenta_Bancaria-Interface';
import { Tipo_Tarjeta } from './Tipo_Tarjeta-Interface';

export interface Tarjeta {
  id: number;
  cuentaBancaria: Cuenta_Bancaria;
  tipoTarjeta: Tipo_Tarjeta;
  codigo: string;
  cvc: string;
  fechaCaducidad: Date;
}
