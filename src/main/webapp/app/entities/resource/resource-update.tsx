import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMetric } from 'app/shared/model/metric.model';
import { getEntities as getMetrics } from 'app/entities/metric/metric.reducer';
import { IBuilding } from 'app/shared/model/building.model';
import { getEntities as getBuildings } from 'app/entities/building/building.reducer';
import { getEntity, updateEntity, createEntity, reset } from './resource.reducer';
import { IResource } from 'app/shared/model/resource.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IResourceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IResourceUpdateState {
  isNew: boolean;
  metricId: string;
  buildingId: string;
}

export class ResourceUpdate extends React.Component<IResourceUpdateProps, IResourceUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      metricId: '0',
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

    this.props.getMetrics();
    this.props.getBuildings();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { resourceEntity } = this.props;
      const entity = {
        ...resourceEntity,
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
    this.props.history.push('/entity/resource');
  };

  render() {
    const { resourceEntity, metrics, buildings, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="divisionCompetitionApp.resource.home.createOrEditLabel">Create or edit a Resource</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : resourceEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="resource-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="countLabel" for="count">
                    Count
                  </Label>
                  <AvField
                    id="resource-count"
                    type="string"
                    className="form-control"
                    name="count"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="typeLabel">Type</Label>
                  <AvInput
                    id="resource-type"
                    type="select"
                    className="form-control"
                    name="type"
                    value={(!isNew && resourceEntity.type) || 'NEGATIVE'}
                  >
                    <option value="NEGATIVE">NEGATIVE</option>
                    <option value="POSITIVE">POSITIVE</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="factorLabel" for="factor">
                    Factor
                  </Label>
                  <AvField id="resource-factor" type="string" className="form-control" name="factor" />
                </AvGroup>
                <AvGroup>
                  <Label for="metric.id">Metric</Label>
                  <AvInput id="resource-metric" type="select" className="form-control" name="metric.id">
                    <option value="" key="0" />
                    {metrics
                      ? metrics.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="building.id">Building</Label>
                  <AvInput id="resource-building" type="select" className="form-control" name="building.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/resource" replace color="info">
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
  metrics: storeState.metric.entities,
  buildings: storeState.building.entities,
  resourceEntity: storeState.resource.entity,
  loading: storeState.resource.loading,
  updating: storeState.resource.updating,
  updateSuccess: storeState.resource.updateSuccess
});

const mapDispatchToProps = {
  getMetrics,
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
)(ResourceUpdate);
