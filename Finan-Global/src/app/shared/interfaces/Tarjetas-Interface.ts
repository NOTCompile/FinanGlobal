export interface Tarjeta {
  id_tarjeta: number;
  id_cuenta: number;
  id_tipo_tarjeta: number;
  codigo: number;
  cvc: number;
  fecha_caducidad: Date;
}
