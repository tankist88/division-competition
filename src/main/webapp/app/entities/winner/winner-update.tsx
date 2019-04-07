import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISubbranch } from 'app/shared/model/subbranch.model';
import { getEntities as getSubbranches } from 'app/entities/subbranch/subbranch.reducer';
import { IBuilding } from 'app/shared/model/building.model';
import { getEntities as getBuildings } from 'app/entities/building/building.reducer';
import { getEntity, updateEntity, createEntity, reset } from './winner.reducer';
import { IWinner } from 'app/shared/model/winner.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IWinnerUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IWinnerUpdateState {
  isNew: boolean;
  subbranchId: string;
  buildingId: string;
}

export class WinnerUpdate extends React.Component<IWinnerUpdateProps, IWinnerUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      subbranchId: '0',
      buildingId: '0',
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
    this.props.getBuildings();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { winnerEntity } = this.props;
      const entity = {
        ...winnerEntity,
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
    this.props.history.push('/entity/winner');
  };

  render() {
    const { winnerEntity, subbranches, buildings, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="divisionCompetitionApp.winner.home.createOrEditLabel">Create or edit a Winner</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : winnerEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="winner-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label for="subbranch.id">Subbranch</Label>
                  <AvInput id="winner-subbranch" type="select" className="form-control" name="subbranch.id">
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
                  <Label for="building.id">Building</Label>
                  <AvInput id="winner-building" type="select" className="form-control" name="building.id">
                    <option value="" key="0" />
                    {buildings
                      ? buildings.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/winner" replace color="info">
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
  buildings: storeState.building.entities,
  winnerEntity: storeState.winner.entity,
  loading: storeState.winner.loading,
  updating: storeState.winner.updating,
  updateSuccess: storeState.winner.updateSuccess
});

const mapDispatchToProps = {
  getSubbranches,
  getBuildings,
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
)(WinnerUpdate);
