import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-not-found',
  standalone: true,
  imports: [RouterLink],
  template: `
    <div class="min-h-screen bg-gray-50 flex flex-col items-center justify-center px-4 text-center">

      <p class="text-8xl font-black text-gray-200 select-none">404</p>

      <h1 class="mt-4 text-2xl font-semibold text-gray-800">Page not found</h1>
      <p class="mt-2 text-sm text-gray-500 max-w-xs">
        The page you're looking for doesn't exist or has been moved.
      </p>

      <a
        routerLink="/dashboard"
        class="mt-8 inline-flex items-center gap-2 bg-blue-600 hover:bg-blue-700 text-white text-sm font-medium px-5 py-2.5 rounded-xl transition-colors"
      >
        ← Back to Dashboard
      </a>

    </div>
  `,
})
export class NotFoundComponent {}
