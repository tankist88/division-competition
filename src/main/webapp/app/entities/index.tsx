import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BattleType from './battle-type';
import BattleMember from './battle-member';
import Subbranch from './subbranch';
import CurrentMetric from './current-metric';
import Metric from './metric';
import Resource from './resource';
import Building from './building';
import ResourceProgress from './resource-progress';
import BuildingProcess from './building-process';
import Employee from './employee';
import Winner from './winner';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/battle-type`} component={BattleType} />
      <ErrorBoundaryRoute path={`${match.url}/battle-member`} component={BattleMember} />
      <ErrorBoundaryRoute path={`${match.url}/subbranch`} component={Subbranch} />
      <ErrorBoundaryRoute path={`${match.url}/current-metric`} component={CurrentMetric} />
      <ErrorBoundaryRoute path={`${match.url}/metric`} component={Metric} />
      <ErrorBoundaryRoute path={`${match.url}/resource`} component={Resource} />
      <ErrorBoundaryRoute path={`${match.url}/building`} component={Building} />
      <ErrorBoundaryRoute path={`${match.url}/resource-progress`} component={ResourceProgress} />
      <ErrorBoundaryRoute path={`${match.url}/building-process`} component={BuildingProcess} />
      <ErrorBoundaryRoute path={`${match.url}/employee`} component={Employee} />
      <ErrorBoundaryRoute path={`${match.url}/winner`} component={Winner} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
