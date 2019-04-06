import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './battle-member.reducer';
import { IBattleMember } from 'app/shared/model/battle-member.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBattleMemberProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class BattleMember extends React.Component<IBattleMemberProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { battleMemberList, match } = this.props;
    return (
      <div>
        <h2 id="battle-member-heading">
          Battle Members
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Battle Member
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Status</th>
                <th>Last Modified</th>
                <th>Subbranch</th>
                <th>Type</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {battleMemberList.map((battleMember, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${battleMember.id}`} color="link" size="sm">
                      {battleMember.id}
                    </Button>
                  </td>
                  <td>{battleMember.status}</td>
                  <td>
                    <TextFormat type="date" value={battleMember.lastModified} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    {battleMember.subbranch ? <Link to={`subbranch/${battleMember.subbranch.id}`}>{battleMember.subbranch.id}</Link> : ''}
                  </td>
                  <td>{battleMember.type ? <Link to={`battle-type/${battleMember.type.id}`}>{battleMember.type.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${battleMember.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${battleMember.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${battleMember.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ battleMember }: IRootState) => ({
  battleMemberList: battleMember.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BattleMember);
