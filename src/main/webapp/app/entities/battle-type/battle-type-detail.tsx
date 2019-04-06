import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './battle-type.reducer';
import { IBattleType } from 'app/shared/model/battle-type.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBattleTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class BattleTypeDetail extends React.Component<IBattleTypeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { battleTypeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            BattleType [<b>{battleTypeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="code">Code</span>
            </dt>
            <dd>{battleTypeEntity.code}</dd>
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{battleTypeEntity.name}</dd>
            <dt>
              <span id="description">Description</span>
            </dt>
            <dd>{battleTypeEntity.description}</dd>
            <dt>
              <span id="termInMonths">Term In Months</span>
            </dt>
            <dd>{battleTypeEntity.termInMonths}</dd>
          </dl>
          <Button tag={Link} to="/entity/battle-type" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/battle-type/${battleTypeEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ battleType }: IRootState) => ({
  battleTypeEntity: battleType.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BattleTypeDetail);
