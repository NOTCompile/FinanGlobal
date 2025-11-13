import { Injectable, signal } from '@angular/core';

@Injectable()
export class ModalStateService {
  private _isOpen = signal(false);
  isOpen = signal(false);

  open() {
    this.isOpen.set(true);
  }

  close() {
    this.isOpen.set(false);
  }

  state() {
    return this._isOpen();
  }
}
