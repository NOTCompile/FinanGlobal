export interface Solicitud {
  id_solicitud: number;
  id_tipo_solicitud: number;
  id_estado_solicitud: number;
  id_producto: number;
  descripcion: string;
  fecha_inicio: Date;
  fecha_fin: Date;
}
