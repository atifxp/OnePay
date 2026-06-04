import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { catchError, map, of } from 'rxjs';
import { UserService } from '../services/user.service';

export const authGuard: CanActivateFn = () => {
  const userService = inject(UserService);
  const router = inject(Router);

  return userService.getProfile().pipe(
    map(() => true), // If the Observable completes successfully, it maps the result to true
    catchError(() => of(router.createUrlTree(['/login']))),
  );
};
