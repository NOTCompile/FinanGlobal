export interface TarjetaDTO {
  id: number;
  codigo: string;
  cvc: string;
  fechaCaducidad: Date;
  cuentaBancaria: {
    id: number;
  };
  tipoTarjeta: {
    id: number;
  };
}
