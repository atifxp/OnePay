import { Routes } from '@angular/router';
import { authGuard } from './guards/auth-guard-guard';
import { adminGuard } from './guards/admin.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },

  // Auth
  {
    path: 'login',
    loadComponent: () => import('./pages/auth/login/login.component').then((m) => m.LoginComponent),
  },
  {
    path: 'register',
    loadComponent: () =>
      import('./pages/auth/register/register.component').then((m) => m.RegisterComponent),
  },

  // Dashboard
  {
    path: 'dashboard',
    loadComponent: () =>
      import('./pages/dashboard/dashboard.component').then((m) => m.DashboardComponent),
    canActivate: [authGuard],
  },

  // Profile
  {
    path: 'profile',
    loadComponent: () =>
      import('./pages/profile/profile.component').then((m) => m.ProfileComponent),
    canActivate: [authGuard],
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
    canActivate: [authGuard],
  },
  {
    path: 'loans/apply',
    loadComponent: () =>
      import('./pages/loans/apply/loan-apply.component').then((m) => m.LoanApplyComponent),
    canActivate: [authGuard],
  },
  {
    path: 'loans/:loanId',
    loadComponent: () =>
      import('./pages/loans/details/loan-details.component').then((m) => m.LoanDetailsComponent),
    canActivate: [authGuard],
  },

  // Wallet
  {
    path: 'wallet',
    loadComponent: () => import('./pages/wallet/wallet.component').then((m) => m.WalletComponent),
    canActivate: [authGuard],
  },

  // Transactions
  {
    path: 'transactions',
    loadComponent: () =>
      import('./pages/transactions/list/transaction-list.component').then(
        (m) => m.TransactionListComponent,
      ),
    canActivate: [authGuard],
  },
  {
    path: 'transactions/:transactionId',
    loadComponent: () =>
      import('./pages/transactions/details/transaction-details.component').then(
        (m) => m.TransactionDetailsComponent,
      ),
    canActivate: [authGuard],
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
];
