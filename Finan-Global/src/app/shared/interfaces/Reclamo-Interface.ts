export interface Reclamo {
  id: number;
  usuario: number;
  tipoReclamo: number;
  estado: number;
  descripcion: string;
  fechaReclamo: Date;
  fechaSolucion: Date;
  detalleSolucion: string;
}
