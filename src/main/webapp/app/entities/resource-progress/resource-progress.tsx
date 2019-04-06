import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './resource-progress.reducer';
import { IResourceProgress } from 'app/shared/model/resource-progress.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IResourceProgressProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class ResourceProgress extends React.Component<IResourceProgressProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { resourceProgressList, match } = this.props;
    return (
      <div>
        <h2 id="resource-progress-heading">
          Resource Progresses
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Resource Progress
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Progress</th>
                <th>Last Modified</th>
                <th>Resource</th>
                <th>Building Process</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {resourceProgressList.map((resourceProgress, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${resourceProgress.id}`} color="link" size="sm">
                      {resourceProgress.id}
                    </Button>
                  </td>
                  <td>{resourceProgress.progress}</td>
                  <td>
                    <TextFormat type="date" value={resourceProgress.lastModified} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    {resourceProgress.resource ? (
                      <Link to={`resource/${resourceProgress.resource.id}`}>{resourceProgress.resource.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {resourceProgress.buildingProcess ? (
                      <Link to={`building-process/${resourceProgress.buildingProcess.id}`}>{resourceProgress.buildingProcess.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${resourceProgress.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${resourceProgress.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${resourceProgress.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ resourceProgress }: IRootState) => ({
  resourceProgressList: resourceProgress.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ResourceProgress);
