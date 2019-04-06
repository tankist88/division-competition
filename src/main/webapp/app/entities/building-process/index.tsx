import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BuildingProcess from './building-process';
import BuildingProcessDetail from './building-process-detail';
import BuildingProcessUpdate from './building-process-update';
import BuildingProcessDeleteDialog from './building-process-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BuildingProcessUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BuildingProcessUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BuildingProcessDetail} />
      <ErrorBoundaryRoute path={match.url} component={BuildingProcess} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={BuildingProcessDeleteDialog} />
  </>
);

export default Routes;
