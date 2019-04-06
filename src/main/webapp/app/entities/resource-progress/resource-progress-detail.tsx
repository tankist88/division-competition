import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './resource-progress.reducer';
import { IResourceProgress } from 'app/shared/model/resource-progress.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IResourceProgressDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ResourceProgressDetail extends React.Component<IResourceProgressDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { resourceProgressEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            ResourceProgress [<b>{resourceProgressEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="progress">Progress</span>
            </dt>
            <dd>{resourceProgressEntity.progress}</dd>
            <dt>
              <span id="lastModified">Last Modified</span>
            </dt>
            <dd>
              <TextFormat value={resourceProgressEntity.lastModified} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>Resource</dt>
            <dd>{resourceProgressEntity.resource ? resourceProgressEntity.resource.id : ''}</dd>
            <dt>Building Process</dt>
            <dd>{resourceProgressEntity.buildingProcess ? resourceProgressEntity.buildingProcess.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/resource-progress" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/resource-progress/${resourceProgressEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ resourceProgress }: IRootState) => ({
  resourceProgressEntity: resourceProgress.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ResourceProgressDetail);
