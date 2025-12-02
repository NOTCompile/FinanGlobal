import { Injectable, signal } from '@angular/core';

@Injectable()
export class ModalCuentaBancariaAdministrador {
  isOpen = signal(false);
  open = () => this.isOpen.set(true);
  close = () => this.isOpen.set(false);
}
