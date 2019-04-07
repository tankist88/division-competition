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
import { getEntity, updateEntity, createEntity, reset } from './building.reducer';
import { IBuilding } from 'app/shared/model/building.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBuildingUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IBuildingUpdateState {
  isNew: boolean;
  winnerId: string;
}

export class BuildingUpdate extends React.Component<IBuildingUpdateProps, IBuildingUpdateState> {
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
      const { buildingEntity } = this.props;
      const entity = {
        ...buildingEntity,
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
    this.props.history.push('/entity/building');
  };

  render() {
    const { buildingEntity, winners, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="divisionCompetitionApp.building.home.createOrEditLabel">Create or edit a Building</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : buildingEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="building-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    Name
                  </Label>
                  <AvField
                    id="building-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="description">
                    Description
                  </Label>
                  <AvField id="building-description" type="text" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label id="siteUrlLabel" for="siteUrl">
                    Site Url
                  </Label>
                  <AvField id="building-siteUrl" type="text" name="siteUrl" />
                </AvGroup>
                <AvGroup>
                  <Label id="pictureFileLabel" for="pictureFile">
                    Picture File
                  </Label>
                  <AvField id="building-pictureFile" type="text" name="pictureFile" />
                </AvGroup>
                <AvGroup>
                  <Label for="winner.id">Winner</Label>
                  <AvInput id="building-winner" type="select" className="form-control" name="winner.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/building" replace color="info">
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
  buildingEntity: storeState.building.entity,
  loading: storeState.building.loading,
  updating: storeState.building.updating,
  updateSuccess: storeState.building.updateSuccess
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
)(BuildingUpdate);
