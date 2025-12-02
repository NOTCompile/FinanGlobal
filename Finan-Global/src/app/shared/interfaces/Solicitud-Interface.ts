export interface Solicitud {
  id: number;
  tipoSolicitud: number;
  estadoSolicitud: number;
  producto: number;
  descripcion: string;
  fechaInicio: Date;
  fechaFin: Date;
}
