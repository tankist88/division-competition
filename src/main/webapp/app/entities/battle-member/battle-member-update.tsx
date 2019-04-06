import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISubbranch } from 'app/shared/model/subbranch.model';
import { getEntities as getSubbranches } from 'app/entities/subbranch/subbranch.reducer';
import { IBattleType } from 'app/shared/model/battle-type.model';
import { getEntities as getBattleTypes } from 'app/entities/battle-type/battle-type.reducer';
import { getEntity, updateEntity, createEntity, reset } from './battle-member.reducer';
import { IBattleMember } from 'app/shared/model/battle-member.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBattleMemberUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IBattleMemberUpdateState {
  isNew: boolean;
  subbranchId: string;
  typeId: string;
}

export class BattleMemberUpdate extends React.Component<IBattleMemberUpdateProps, IBattleMemberUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      subbranchId: '0',
      typeId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getSubbranches();
    this.props.getBattleTypes();
  }

  saveEntity = (event, errors, values) => {
    values.lastModified = convertDateTimeToServer(values.lastModified);

    if (errors.length === 0) {
      const { battleMemberEntity } = this.props;
      const entity = {
        ...battleMemberEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/battle-member');
  };

  render() {
    const { battleMemberEntity, subbranches, battleTypes, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="divisionCompetitionApp.battleMember.home.createOrEditLabel">Create or edit a BattleMember</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : battleMemberEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="battle-member-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="statusLabel" for="status">
                    Status
                  </Label>
                  <AvField
                    id="battle-member-status"
                    type="string"
                    className="form-control"
                    name="status"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="lastModifiedLabel" for="lastModified">
                    Last Modified
                  </Label>
                  <AvInput
                    id="battle-member-lastModified"
                    type="datetime-local"
                    className="form-control"
                    name="lastModified"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.battleMemberEntity.lastModified)}
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="subbranch.id">Subbranch</Label>
                  <AvInput id="battle-member-subbranch" type="select" className="form-control" name="subbranch.id">
                    <option value="" key="0" />
                    {subbranches
                      ? subbranches.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="type.id">Type</Label>
                  <AvInput id="battle-member-type" type="select" className="form-control" name="type.id">
                    <option value="" key="0" />
                    {battleTypes
                      ? battleTypes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/battle-member" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  subbranches: storeState.subbranch.entities,
  battleTypes: storeState.battleType.entities,
  battleMemberEntity: storeState.battleMember.entity,
  loading: storeState.battleMember.loading,
  updating: storeState.battleMember.updating,
  updateSuccess: storeState.battleMember.updateSuccess
});

const mapDispatchToProps = {
  getSubbranches,
  getBattleTypes,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BattleMemberUpdate);
