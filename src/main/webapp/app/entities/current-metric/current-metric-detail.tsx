import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './current-metric.reducer';
import { ICurrentMetric } from 'app/shared/model/current-metric.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICurrentMetricDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CurrentMetricDetail extends React.Component<ICurrentMetricDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { currentMetricEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            CurrentMetric [<b>{currentMetricEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="count">Count</span>
            </dt>
            <dd>{currentMetricEntity.count}</dd>
            <dt>
              <span id="finalizedCount">Finalized Count</span>
            </dt>
            <dd>{currentMetricEntity.finalizedCount}</dd>
            <dt>
              <span id="finalizeDate">Finalize Date</span>
            </dt>
            <dd>
              <TextFormat value={currentMetricEntity.finalizeDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="lastModified">Last Modified</span>
            </dt>
            <dd>
              <TextFormat value={currentMetricEntity.lastModified} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>Subbranch</dt>
            <dd>{currentMetricEntity.subbranch ? currentMetricEntity.subbranch.id : ''}</dd>
            <dt>Metric</dt>
            <dd>{currentMetricEntity.metric ? currentMetricEntity.metric.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/current-metric" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/current-metric/${currentMetricEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ currentMetric }: IRootState) => ({
  currentMetricEntity: currentMetric.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CurrentMetricDetail);
