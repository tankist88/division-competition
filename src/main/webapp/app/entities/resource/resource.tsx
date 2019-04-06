import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './resource.reducer';
import { IResource } from 'app/shared/model/resource.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IResourceProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Resource extends React.Component<IResourceProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { resourceList, match } = this.props;
    return (
      <div>
        <h2 id="resource-heading">
          Resources
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Resource
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Count</th>
                <th>Type</th>
                <th>Factor</th>
                <th>Metric</th>
                <th>Building</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {resourceList.map((resource, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${resource.id}`} color="link" size="sm">
                      {resource.id}
                    </Button>
                  </td>
                  <td>{resource.count}</td>
                  <td>{resource.type}</td>
                  <td>{resource.factor}</td>
                  <td>{resource.metric ? <Link to={`metric/${resource.metric.id}`}>{resource.metric.id}</Link> : ''}</td>
                  <td>{resource.building ? <Link to={`building/${resource.building.id}`}>{resource.building.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${resource.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${resource.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${resource.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ resource }: IRootState) => ({
  resourceList: resource.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Resource);
