import { Component, inject, signal} from '@angular/core';
import { CommonModule } from '@angular/common';

type TipoMensaje = 'success' | 'warning' | 'error';

@Component({
  selector: 'app-notification',
  imports: [CommonModule],
  standalone: true,
  templateUrl: './notification.component.html',
  styleUrl: './notification.component.css',
})
export class NotificationComponent {
  private static _mensaje = signal<string | null>(null);
  private static _tipo = signal<TipoMensaje>('success');

  mensaje = NotificationComponent._mensaje;
  tipo = NotificationComponent._tipo;

  icono() {
    switch (this.tipo()) {
      case 'success':
        return '✅';
      case 'warning':
        return '⚠️';
      case 'error':
        return '❌';
    }
  }

  static mostrar(mensaje: string, tipo: TipoMensaje = 'success') {
    NotificationComponent._mensaje.set(mensaje);
    NotificationComponent._tipo.set(tipo);
    setTimeout(() => NotificationComponent._mensaje.set(null), 5000);
  }
}
