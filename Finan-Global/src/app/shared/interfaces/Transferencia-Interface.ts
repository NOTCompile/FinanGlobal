export interface Tranferencia {
  id: number;
  usuarioEmisor: number;
  cuentaEmisora: number;
  usuarioReceptor: number;
  cuentaReceptora: number;
  tipoTransferencia: number;
  monto: number;
  fecha: Date;
}
