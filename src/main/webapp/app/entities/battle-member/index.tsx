import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BattleMember from './battle-member';
import BattleMemberDetail from './battle-member-detail';
import BattleMemberUpdate from './battle-member-update';
import BattleMemberDeleteDialog from './battle-member-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BattleMemberUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BattleMemberUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BattleMemberDetail} />
      <ErrorBoundaryRoute path={match.url} component={BattleMember} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={BattleMemberDeleteDialog} />
  </>
);

export default Routes;
