import { Injectable, signal } from '@angular/core';

@Injectable()
export class ModalUsuarioAdministrador {
  isOpen = signal(false);
  open = () => this.isOpen.set(true);
  close = () => this.isOpen.set(false);
}
