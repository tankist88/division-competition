import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './metric.reducer';
import { IMetric } from 'app/shared/model/metric.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMetricUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IMetricUpdateState {
  isNew: boolean;
}

export class MetricUpdate extends React.Component<IMetricUpdateProps, IMetricUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { metricEntity } = this.props;
      const entity = {
        ...metricEntity,
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
    this.props.history.push('/entity/metric');
  };

  render() {
    const { metricEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="divisionCompetitionApp.metric.home.createOrEditLabel">Create or edit a Metric</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : metricEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="metric-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="metricIdLabel" for="metricId">
                    Metric Id
                  </Label>
                  <AvField
                    id="metric-metricId"
                    type="string"
                    className="form-control"
                    name="metricId"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="metricNameLabel" for="metricName">
                    Metric Name
                  </Label>
                  <AvField
                    id="metric-metricName"
                    type="text"
                    name="metricName"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="termTypeLabel">Term Type</Label>
                  <AvInput
                    id="metric-termType"
                    type="select"
                    className="form-control"
                    name="termType"
                    value={(!isNew && metricEntity.termType) || 'FIXED_DAY_OF_MONTH'}
                  >
                    <option value="FIXED_DAY_OF_MONTH">FIXED_DAY_OF_MONTH</option>
                    <option value="N_WORK_DAY_OF_MONTH">N_WORK_DAY_OF_MONTH</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="termLabel" for="term">
                    Term
                  </Label>
                  <AvField
                    id="metric-term"
                    type="string"
                    className="form-control"
                    name="term"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/metric" replace color="info">
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
  metricEntity: storeState.metric.entity,
  loading: storeState.metric.loading,
  updating: storeState.metric.updating,
  updateSuccess: storeState.metric.updateSuccess
});

const mapDispatchToProps = {
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
)(MetricUpdate);
