import { Estado_Producto } from './Estado_Producto-Interface';
import { Producto } from './Producto-Interface';

export interface Empeno {
  id: number;
  producto: Producto;
  valorPrestado: number;
  valorRecuperacion: number;
  fechaInicio: Date;
  fechaFinal: Date;
  estado: Estado_Producto;
}
