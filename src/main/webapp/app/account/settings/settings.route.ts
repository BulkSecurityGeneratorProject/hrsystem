import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SettingsComponent } from './settings.component';

export const settingsRoute: Route = {
    path: 'settings',
    component: SettingsComponent,
    data: {
        authorities: ['ROLE_USER', 'ROLE_EMPLOYEE', 'ROLE_HR', 'ROLE_IT'],
        pageTitle: 'Settings'
    },
    canActivate: [UserRouteAccessService]
};
