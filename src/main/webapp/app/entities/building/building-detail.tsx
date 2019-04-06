import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './building.reducer';
import { IBuilding } from 'app/shared/model/building.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBuildingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class BuildingDetail extends React.Component<IBuildingDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { buildingEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Building [<b>{buildingEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{buildingEntity.name}</dd>
            <dt>
              <span id="description">Description</span>
            </dt>
            <dd>{buildingEntity.description}</dd>
            <dt>
              <span id="siteUrl">Site Url</span>
            </dt>
            <dd>{buildingEntity.siteUrl}</dd>
            <dt>
              <span id="pictureFile">Picture File</span>
            </dt>
            <dd>{buildingEntity.pictureFile}</dd>
          </dl>
          <Button tag={Link} to="/entity/building" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/building/${buildingEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ building }: IRootState) => ({
  buildingEntity: building.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BuildingDetail);
