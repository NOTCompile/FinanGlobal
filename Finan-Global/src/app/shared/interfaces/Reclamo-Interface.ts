export interface Reclamo {
  id_reclamo: number;
  id_usuario: number;
  id_tipo_reclamo: number;
  id_estado_reclamo: number;
  descripcion: string;
  fecha_reclamo: Date;
  fecha_solucion: Date;
  detalle_solucion: string;
}
