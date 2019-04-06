import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './resource.reducer';
import { IResource } from 'app/shared/model/resource.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IResourceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ResourceDetail extends React.Component<IResourceDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { resourceEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Resource [<b>{resourceEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="count">Count</span>
            </dt>
            <dd>{resourceEntity.count}</dd>
            <dt>
              <span id="type">Type</span>
            </dt>
            <dd>{resourceEntity.type}</dd>
            <dt>
              <span id="factor">Factor</span>
            </dt>
            <dd>{resourceEntity.factor}</dd>
            <dt>Metric</dt>
            <dd>{resourceEntity.metric ? resourceEntity.metric.id : ''}</dd>
            <dt>Building</dt>
            <dd>{resourceEntity.building ? resourceEntity.building.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/resource" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/resource/${resourceEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ resource }: IRootState) => ({
  resourceEntity: resource.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ResourceDetail);
