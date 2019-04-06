import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './building-process.reducer';
import { IBuildingProcess } from 'app/shared/model/building-process.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBuildingProcessProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class BuildingProcess extends React.Component<IBuildingProcessProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { buildingProcessList, match } = this.props;
    return (
      <div>
        <h2 id="building-process-heading">
          Building Processes
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Building Process
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Subbranch</th>
                <th>Building</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {buildingProcessList.map((buildingProcess, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${buildingProcess.id}`} color="link" size="sm">
                      {buildingProcess.id}
                    </Button>
                  </td>
                  <td>
                    {buildingProcess.subbranch ? (
                      <Link to={`subbranch/${buildingProcess.subbranch.id}`}>{buildingProcess.subbranch.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {buildingProcess.building ? (
                      <Link to={`building/${buildingProcess.building.id}`}>{buildingProcess.building.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${buildingProcess.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${buildingProcess.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${buildingProcess.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ buildingProcess }: IRootState) => ({
  buildingProcessList: buildingProcess.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BuildingProcess);
