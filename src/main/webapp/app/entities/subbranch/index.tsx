import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Subbranch from './subbranch';
import SubbranchDetail from './subbranch-detail';
import SubbranchUpdate from './subbranch-update';
import SubbranchDeleteDialog from './subbranch-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SubbranchUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SubbranchUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SubbranchDetail} />
      <ErrorBoundaryRoute path={match.url} component={Subbranch} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={SubbranchDeleteDialog} />
  </>
);

export default Routes;
