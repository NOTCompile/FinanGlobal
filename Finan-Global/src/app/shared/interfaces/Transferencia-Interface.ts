export interface Tranferencia {
  id_transferencia: number;
  id_usuario_emisor: number;
  id_cuenta_emisor: number;
  id_usuario_receptor: number;
  id_cuenta_receptor: number;
  id_tipo_transferencia: number;
  monto: number;
  fecha: Date;
}
