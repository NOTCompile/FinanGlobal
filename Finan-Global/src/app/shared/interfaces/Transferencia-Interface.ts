import { Cuenta_Bancaria } from './Cuenta_Bancaria-Interface';
import { Tipo_Transferencia } from './Tipo_Transferencia-Interface';
import { Usuario } from './Usuario-Interface';

export interface Tranferencia {
  id: number;
  usuarioEmisor: Usuario;
  cuentaEmisora: Cuenta_Bancaria;
  usuarioReceptor: Usuario;
  cuentaReceptora: Cuenta_Bancaria;
  tipoTransferencia: Tipo_Transferencia;
  monto: number;
  fecha: Date;
}
