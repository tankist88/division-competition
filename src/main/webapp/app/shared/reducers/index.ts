import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
import sessions, { SessionsState } from 'app/modules/account/sessions/sessions.reducer';
// prettier-ignore
import battleType, {
  BattleTypeState
} from 'app/entities/battle-type/battle-type.reducer';
// prettier-ignore
import battleMember, {
  BattleMemberState
} from 'app/entities/battle-member/battle-member.reducer';
// prettier-ignore
import subbranch, {
  SubbranchState
} from 'app/entities/subbranch/subbranch.reducer';
// prettier-ignore
import currentMetric, {
  CurrentMetricState
} from 'app/entities/current-metric/current-metric.reducer';
// prettier-ignore
import metric, {
  MetricState
} from 'app/entities/metric/metric.reducer';
// prettier-ignore
import resource, {
  ResourceState
} from 'app/entities/resource/resource.reducer';
// prettier-ignore
import building, {
  BuildingState
} from 'app/entities/building/building.reducer';
// prettier-ignore
import resourceProgress, {
  ResourceProgressState
} from 'app/entities/resource-progress/resource-progress.reducer';
// prettier-ignore
import buildingProcess, {
  BuildingProcessState
} from 'app/entities/building-process/building-process.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly sessions: SessionsState;
  readonly battleType: BattleTypeState;
  readonly battleMember: BattleMemberState;
  readonly subbranch: SubbranchState;
  readonly currentMetric: CurrentMetricState;
  readonly metric: MetricState;
  readonly resource: ResourceState;
  readonly building: BuildingState;
  readonly resourceProgress: ResourceProgressState;
  readonly buildingProcess: BuildingProcessState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  sessions,
  battleType,
  battleMember,
  subbranch,
  currentMetric,
  metric,
  resource,
  building,
  resourceProgress,
  buildingProcess,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
