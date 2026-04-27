import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';
import { adminGuard } from './guards/admin.guard';
import { pendingVerificationGuard } from './guards/pending-verification-guard';
import { publicRoutesGuard } from './guards/public-routes-guard';
import { approvedVerificationGuard } from './guards/approved-verification-guard';

export const routes: Routes = [
  // Homepage
  {
    path: '',
    loadComponent: () => import('./pages/home/home.component').then((m) => m.HomeComponent),
    canActivate: [publicRoutesGuard],
  },

  // Auth
  {
    path: 'login',
    loadComponent: () => import('./pages/auth/login/login.component').then((m) => m.LoginComponent),
    canActivate: [publicRoutesGuard],
  },
  {
    path: 'register',
    loadComponent: () =>
      import('./pages/auth/register/register.component').then((m) => m.RegisterComponent),
    canActivate: [publicRoutesGuard],
  },

  // Dashboard
  {
    path: 'dashboard',
    loadComponent: () =>
      import('./pages/dashboard/dashboard.component').then((m) => m.DashboardComponent),
    canActivate: [authGuard, pendingVerificationGuard],
  },

  // Profile
  {
    path: 'profile',
    loadComponent: () =>
      import('./pages/profile/profile.component').then((m) => m.ProfileComponent),
    canActivate: [authGuard, pendingVerificationGuard],
  },

  // Verification
  {
    path: 'verification/submit',
    loadComponent: () =>
      import('./pages/verification/submit/verification-submit.component').then(
        (m) => m.VerificationSubmitComponent,
      ),
    canActivate: [authGuard],
  },
  {
    path: 'verification/status',
    loadComponent: () =>
      import('./pages/verification/status/verification-status.component').then(
        (m) => m.VerificationStatusComponent,
      ),
    canActivate: [authGuard],
  },

  // Loans
  {
    path: 'loans',
    loadComponent: () =>
      import('./pages/loans/list/loan-list.component').then((m) => m.LoanListComponent),
    canActivate: [authGuard, pendingVerificationGuard],
  },
  {
    path: 'loans/apply',
    loadComponent: () =>
      import('./pages/loans/apply/loan-apply.component').then((m) => m.LoanApplyComponent),
    canActivate: [authGuard, pendingVerificationGuard],
  },
  {
    path: 'loans/:loanId',
    loadComponent: () =>
      import('./pages/loans/details/loan-details.component').then((m) => m.LoanDetailsComponent),
    canActivate: [authGuard, pendingVerificationGuard],
  },

  // Wallet
  {
    path: 'wallet',
    loadComponent: () => import('./pages/wallet/wallet.component').then((m) => m.WalletComponent),
    canActivate: [authGuard, pendingVerificationGuard],
  },

  // Transactions
  {
    path: 'transactions',
    loadComponent: () =>
      import('./pages/transactions/list/transaction-list.component').then(
        (m) => m.TransactionListComponent,
      ),
    canActivate: [authGuard, pendingVerificationGuard],
  },
  {
    path: 'transactions/:transactionId',
    loadComponent: () =>
      import('./pages/transactions/details/transaction-details.component').then(
        (m) => m.TransactionDetailsComponent,
      ),
    canActivate: [authGuard, pendingVerificationGuard],
  },

  // Admin
  {
    path: 'admin/verification',
    loadComponent: () =>
      import('./pages/admin/verification/admin-verification.component').then(
        (m) => m.AdminVerificationComponent,
      ),
    canActivate: [adminGuard],
  },
  {
    path: 'admin/loans',
    loadComponent: () =>
      import('./pages/admin/loans/admin-loans.component').then((m) => m.AdminLoansComponent),
    canActivate: [adminGuard],
  },
  {
    path: 'admin/transactions',
    loadComponent: () =>
      import('./pages/admin/transactions/admin-transactions.component').then(
        (m) => m.AdminTransactionsComponent,
      ),
    canActivate: [adminGuard],
  },
  {
    path: 'admin/promote',
    loadComponent: () =>
      import('./pages/admin/promote-user/promote-user.component').then(
        (m) => m.PromoteUserComponent,
      ),
    canActivate: [adminGuard],
  },

  // 404 page
  {
    path: '**',
    loadComponent: () =>
      import('./pages/not-found/not-found.component').then((m) => m.NotFoundComponent),
  },
];
