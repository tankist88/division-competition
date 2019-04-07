import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IWinner } from 'app/shared/model/winner.model';
import { getEntities as getWinners } from 'app/entities/winner/winner.reducer';
import { getEntity, updateEntity, createEntity, reset } from './subbranch.reducer';
import { ISubbranch } from 'app/shared/model/subbranch.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISubbranchUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ISubbranchUpdateState {
  isNew: boolean;
  winnerId: string;
}

export class SubbranchUpdate extends React.Component<ISubbranchUpdateProps, ISubbranchUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      winnerId: '0',
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

    this.props.getWinners();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { subbranchEntity } = this.props;
      const entity = {
        ...subbranchEntity,
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
    this.props.history.push('/entity/subbranch');
  };

  render() {
    const { subbranchEntity, winners, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="divisionCompetitionApp.subbranch.home.createOrEditLabel">Create or edit a Subbranch</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : subbranchEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="subbranch-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="tbLabel" for="tb">
                    Tb
                  </Label>
                  <AvField
                    id="subbranch-tb"
                    type="string"
                    className="form-control"
                    name="tb"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="branchLabel" for="branch">
                    Branch
                  </Label>
                  <AvField id="subbranch-branch" type="string" className="form-control" name="branch" />
                </AvGroup>
                <AvGroup>
                  <Label id="subbranchLabel" for="subbranch">
                    Subbranch
                  </Label>
                  <AvField id="subbranch-subbranch" type="string" className="form-control" name="subbranch" />
                </AvGroup>
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    Name
                  </Label>
                  <AvField id="subbranch-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label for="winner.id">Winner</Label>
                  <AvInput id="subbranch-winner" type="select" className="form-control" name="winner.id">
                    <option value="" key="0" />
                    {winners
                      ? winners.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/subbranch" replace color="info">
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
  winners: storeState.winner.entities,
  subbranchEntity: storeState.subbranch.entity,
  loading: storeState.subbranch.loading,
  updating: storeState.subbranch.updating,
  updateSuccess: storeState.subbranch.updateSuccess
});

const mapDispatchToProps = {
  getWinners,
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
)(SubbranchUpdate);
