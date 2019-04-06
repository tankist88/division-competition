import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ResourceProgress from './resource-progress';
import ResourceProgressDetail from './resource-progress-detail';
import ResourceProgressUpdate from './resource-progress-update';
import ResourceProgressDeleteDialog from './resource-progress-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ResourceProgressUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ResourceProgressUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ResourceProgressDetail} />
      <ErrorBoundaryRoute path={match.url} component={ResourceProgress} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ResourceProgressDeleteDialog} />
  </>
);

export default Routes;
