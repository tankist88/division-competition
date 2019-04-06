import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './metric.reducer';
import { IMetric } from 'app/shared/model/metric.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMetricDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MetricDetail extends React.Component<IMetricDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { metricEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Metric [<b>{metricEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="metricId">Metric Id</span>
            </dt>
            <dd>{metricEntity.metricId}</dd>
            <dt>
              <span id="metricName">Metric Name</span>
            </dt>
            <dd>{metricEntity.metricName}</dd>
            <dt>
              <span id="termType">Term Type</span>
            </dt>
            <dd>{metricEntity.termType}</dd>
            <dt>
              <span id="term">Term</span>
            </dt>
            <dd>{metricEntity.term}</dd>
          </dl>
          <Button tag={Link} to="/entity/metric" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/metric/${metricEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ metric }: IRootState) => ({
  metricEntity: metric.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MetricDetail);
