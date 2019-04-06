import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './building-process.reducer';
import { IBuildingProcess } from 'app/shared/model/building-process.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBuildingProcessDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class BuildingProcessDetail extends React.Component<IBuildingProcessDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { buildingProcessEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            BuildingProcess [<b>{buildingProcessEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>Subbranch</dt>
            <dd>{buildingProcessEntity.subbranch ? buildingProcessEntity.subbranch.id : ''}</dd>
            <dt>Building</dt>
            <dd>{buildingProcessEntity.building ? buildingProcessEntity.building.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/building-process" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/building-process/${buildingProcessEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ buildingProcess }: IRootState) => ({
  buildingProcessEntity: buildingProcess.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BuildingProcessDetail);
