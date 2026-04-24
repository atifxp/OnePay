import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },

  // Auth
  { path: 'login', loadComponent: () => import('./pages/auth/login/login.component').then(m => m.LoginComponent) },
  { path: 'register', loadComponent: () => import('./pages/auth/register/register.component').then(m => m.RegisterComponent) },

  // Dashboard
  { path: 'dashboard', loadComponent: () => import('./pages/dashboard/dashboard.component').then(m => m.DashboardComponent) },

  // Profile
  { path: 'profile', loadComponent: () => import('./pages/profile/profile.component').then(m => m.ProfileComponent) },

  // Verification
  { path: 'verification/submit', loadComponent: () => import('./pages/verification/submit/verification-submit.component').then(m => m.VerificationSubmitComponent) },
  { path: 'verification/status', loadComponent: () => import('./pages/verification/status/verification-status.component').then(m => m.VerificationStatusComponent) },

  // Loans
  { path: 'loans', loadComponent: () => import('./pages/loans/list/loan-list.component').then(m => m.LoanListComponent) },
  { path: 'loans/apply', loadComponent: () => import('./pages/loans/apply/loan-apply.component').then(m => m.LoanApplyComponent) },
  { path: 'loans/:loanId', loadComponent: () => import('./pages/loans/details/loan-details.component').then(m => m.LoanDetailsComponent) },

  // Wallet
  { path: 'wallet', loadComponent: () => import('./pages/wallet/wallet.component').then(m => m.WalletComponent) },

  // Transactions
  { path: 'transactions', loadComponent: () => import('./pages/transactions/list/transaction-list.component').then(m => m.TransactionListComponent) },
  { path: 'transactions/:transactionId', loadComponent: () => import('./pages/transactions/details/transaction-details.component').then(m => m.TransactionDetailsComponent) },

  // Admin
  { path: 'admin/verification', loadComponent: () => import('./pages/admin/verification/admin-verification.component').then(m => m.AdminVerificationComponent) },
  { path: 'admin/loans', loadComponent: () => import('./pages/admin/loans/admin-loans.component').then(m => m.AdminLoansComponent) },
  { path: 'admin/transactions', loadComponent: () => import('./pages/admin/transactions/admin-transactions.component').then(m => m.AdminTransactionsComponent) },
];
