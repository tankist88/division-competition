import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Winner from './winner';
import WinnerDetail from './winner-detail';
import WinnerUpdate from './winner-update';
import WinnerDeleteDialog from './winner-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WinnerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WinnerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WinnerDetail} />
      <ErrorBoundaryRoute path={match.url} component={Winner} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={WinnerDeleteDialog} />
  </>
);

export default Routes;
