import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './battle-member.reducer';
import { IBattleMember } from 'app/shared/model/battle-member.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBattleMemberDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class BattleMemberDetail extends React.Component<IBattleMemberDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { battleMemberEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            BattleMember [<b>{battleMemberEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{battleMemberEntity.status}</dd>
            <dt>
              <span id="lastModified">Last Modified</span>
            </dt>
            <dd>
              <TextFormat value={battleMemberEntity.lastModified} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>Subbranch</dt>
            <dd>{battleMemberEntity.subbranch ? battleMemberEntity.subbranch.id : ''}</dd>
            <dt>Type</dt>
            <dd>{battleMemberEntity.type ? battleMemberEntity.type.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/battle-member" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/battle-member/${battleMemberEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ battleMember }: IRootState) => ({
  battleMemberEntity: battleMember.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BattleMemberDetail);
