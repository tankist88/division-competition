import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CurrentMetric from './current-metric';
import CurrentMetricDetail from './current-metric-detail';
import CurrentMetricUpdate from './current-metric-update';
import CurrentMetricDeleteDialog from './current-metric-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CurrentMetricUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CurrentMetricUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CurrentMetricDetail} />
      <ErrorBoundaryRoute path={match.url} component={CurrentMetric} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CurrentMetricDeleteDialog} />
  </>
);

export default Routes;
