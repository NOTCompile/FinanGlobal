import { Usuario } from './Usuario-Interface';

export interface Cuenta_Bancaria {
  id: number;
  ncuenta: string;
  n_intercuenta: string;
  nombre: string;
  saldo: number;
  usuario: Usuario;
}
