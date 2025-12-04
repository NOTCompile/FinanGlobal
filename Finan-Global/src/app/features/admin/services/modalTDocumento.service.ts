import { Injectable, signal } from '@angular/core';

@Injectable()
export class ModalTDocumentoAdministrador {
  isOpen = signal(false);
  open = () => this.isOpen.set(true);
  close = () => this.isOpen.set(false);
}
