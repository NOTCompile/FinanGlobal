export interface Tarjeta {
  id: number;
  cuentaBancaria: number;
  tipoTarjeta: number;
  codigo: string;
  cvc: string;
  fechaCaducidad: Date;
}
