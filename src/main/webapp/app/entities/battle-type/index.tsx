import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BattleType from './battle-type';
import BattleTypeDetail from './battle-type-detail';
import BattleTypeUpdate from './battle-type-update';
import BattleTypeDeleteDialog from './battle-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BattleTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BattleTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BattleTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={BattleType} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={BattleTypeDeleteDialog} />
  </>
);

export default Routes;
