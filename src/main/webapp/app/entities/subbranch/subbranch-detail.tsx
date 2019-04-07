import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './subbranch.reducer';
import { ISubbranch } from 'app/shared/model/subbranch.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISubbranchDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class SubbranchDetail extends React.Component<ISubbranchDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { subbranchEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Subbranch [<b>{subbranchEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="tb">Tb</span>
            </dt>
            <dd>{subbranchEntity.tb}</dd>
            <dt>
              <span id="branch">Branch</span>
            </dt>
            <dd>{subbranchEntity.branch}</dd>
            <dt>
              <span id="subbranch">Subbranch</span>
            </dt>
            <dd>{subbranchEntity.subbranch}</dd>
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{subbranchEntity.name}</dd>
            <dt>Winner</dt>
            <dd>{subbranchEntity.winner ? subbranchEntity.winner.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/subbranch" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/subbranch/${subbranchEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ subbranch }: IRootState) => ({
  subbranchEntity: subbranch.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SubbranchDetail);
