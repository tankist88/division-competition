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
import { IMetric } from 'app/shared/model/metric.model';
import { getEntities as getMetrics } from 'app/entities/metric/metric.reducer';
import { getEntity, updateEntity, createEntity, reset } from './current-metric.reducer';
import { ICurrentMetric } from 'app/shared/model/current-metric.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICurrentMetricUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICurrentMetricUpdateState {
  isNew: boolean;
  subbranchId: string;
  metricId: string;
}

export class CurrentMetricUpdate extends React.Component<ICurrentMetricUpdateProps, ICurrentMetricUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      subbranchId: '0',
      metricId: '0',
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
    this.props.getMetrics();
  }

  saveEntity = (event, errors, values) => {
    values.finalizeDate = convertDateTimeToServer(values.finalizeDate);
    values.lastModified = convertDateTimeToServer(values.lastModified);

    if (errors.length === 0) {
      const { currentMetricEntity } = this.props;
      const entity = {
        ...currentMetricEntity,
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
    this.props.history.push('/entity/current-metric');
  };

  render() {
    const { currentMetricEntity, subbranches, metrics, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="divisionCompetitionApp.currentMetric.home.createOrEditLabel">Create or edit a CurrentMetric</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : currentMetricEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="current-metric-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="countLabel" for="count">
                    Count
                  </Label>
                  <AvField id="current-metric-count" type="string" className="form-control" name="count" />
                </AvGroup>
                <AvGroup>
                  <Label id="finalizedCountLabel" for="finalizedCount">
                    Finalized Count
                  </Label>
                  <AvField id="current-metric-finalizedCount" type="string" className="form-control" name="finalizedCount" />
                </AvGroup>
                <AvGroup>
                  <Label id="finalizeDateLabel" for="finalizeDate">
                    Finalize Date
                  </Label>
                  <AvInput
                    id="current-metric-finalizeDate"
                    type="datetime-local"
                    className="form-control"
                    name="finalizeDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.currentMetricEntity.finalizeDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="lastModifiedLabel" for="lastModified">
                    Last Modified
                  </Label>
                  <AvInput
                    id="current-metric-lastModified"
                    type="datetime-local"
                    className="form-control"
                    name="lastModified"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.currentMetricEntity.lastModified)}
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="subbranch.id">Subbranch</Label>
                  <AvInput id="current-metric-subbranch" type="select" className="form-control" name="subbranch.id">
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
                  <Label for="metric.id">Metric</Label>
                  <AvInput id="current-metric-metric" type="select" className="form-control" name="metric.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/current-metric" replace color="info">
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
  metrics: storeState.metric.entities,
  currentMetricEntity: storeState.currentMetric.entity,
  loading: storeState.currentMetric.loading,
  updating: storeState.currentMetric.updating,
  updateSuccess: storeState.currentMetric.updateSuccess
});

const mapDispatchToProps = {
  getSubbranches,
  getMetrics,
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
)(CurrentMetricUpdate);
