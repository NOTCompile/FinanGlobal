import { Component, inject, OnInit } from '@angular/core';
import { EmpenoService } from 'src/app/shared/services/Empeno.service';
import { ProductoService } from 'src/app/shared/services/Producto.service';

@Component({
  selector: 'app-pawnshop-page',
  imports: [],
  templateUrl: './pawnshop-page.component.html',
  styleUrl: './pawnshop-page.component.css',
})
export default class PawnshopPageComponent implements OnInit {
  private empenioService = inject(EmpenoService);
  private productoService = inject(ProductoService);

  empenios = this.empenioService.empenos;

  ngOnInit(): void {
    this.cargarEmpenios();
    this.cargarProductos();
  }

  // Cargar Empeños
  cargarEmpenios(): void {
    this.empenioService.getProductoOfEmpeno().subscribe({
      next: (data) => {
        this.empenios.set(data);
        console.log('Empeños Cargados');
      },
      error: (err) => console.error('Error al cargar los empeñios: ', err),
    });
  }

  // Cargar Productos
  cargarProductos(): void {
    this.productoService.getAll().subscribe({
      next: () => {
        console.log('Productos Cargados');
      },
      error: (err) => console.error('Error al cargar los productos: ', err),
    });
  }
}
