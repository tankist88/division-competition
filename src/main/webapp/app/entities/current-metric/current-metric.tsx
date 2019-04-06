import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './current-metric.reducer';
import { ICurrentMetric } from 'app/shared/model/current-metric.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICurrentMetricProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class CurrentMetric extends React.Component<ICurrentMetricProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { currentMetricList, match } = this.props;
    return (
      <div>
        <h2 id="current-metric-heading">
          Current Metrics
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Current Metric
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Count</th>
                <th>Finalized Count</th>
                <th>Finalize Date</th>
                <th>Last Modified</th>
                <th>Subbranch</th>
                <th>Metric</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {currentMetricList.map((currentMetric, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${currentMetric.id}`} color="link" size="sm">
                      {currentMetric.id}
                    </Button>
                  </td>
                  <td>{currentMetric.count}</td>
                  <td>{currentMetric.finalizedCount}</td>
                  <td>
                    <TextFormat type="date" value={currentMetric.finalizeDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={currentMetric.lastModified} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    {currentMetric.subbranch ? (
                      <Link to={`subbranch/${currentMetric.subbranch.id}`}>{currentMetric.subbranch.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{currentMetric.metric ? <Link to={`metric/${currentMetric.metric.id}`}>{currentMetric.metric.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${currentMetric.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${currentMetric.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${currentMetric.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ currentMetric }: IRootState) => ({
  currentMetricList: currentMetric.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CurrentMetric);
