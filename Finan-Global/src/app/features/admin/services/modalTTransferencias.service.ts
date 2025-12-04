import { Injectable, signal } from '@angular/core';

@Injectable()
export class ModalTTransfrenciasAdministrador {
  isOpen = signal(false);
  open = () => this.isOpen.set(true);
  close = () => this.isOpen.set(false);
}
