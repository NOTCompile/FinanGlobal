import { Injectable, signal } from '@angular/core';

@Injectable()
export class ModalTransferenciaAdministrador {
  isOpen = signal(false);
  open = () => this.isOpen.set(true);
  close = () => this.isOpen.set(false);
}
