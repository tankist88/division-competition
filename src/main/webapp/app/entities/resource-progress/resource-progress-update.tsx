import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IResource } from 'app/shared/model/resource.model';
import { getEntities as getResources } from 'app/entities/resource/resource.reducer';
import { IBuildingProcess } from 'app/shared/model/building-process.model';
import { getEntities as getBuildingProcesses } from 'app/entities/building-process/building-process.reducer';
import { getEntity, updateEntity, createEntity, reset } from './resource-progress.reducer';
import { IResourceProgress } from 'app/shared/model/resource-progress.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IResourceProgressUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IResourceProgressUpdateState {
  isNew: boolean;
  resourceId: string;
  buildingProcessId: string;
}

export class ResourceProgressUpdate extends React.Component<IResourceProgressUpdateProps, IResourceProgressUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      resourceId: '0',
      buildingProcessId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getResources();
    this.props.getBuildingProcesses();
  }

  saveEntity = (event, errors, values) => {
    values.lastModified = convertDateTimeToServer(values.lastModified);

    if (errors.length === 0) {
      const { resourceProgressEntity } = this.props;
      const entity = {
        ...resourceProgressEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/resource-progress');
  };

  render() {
    const { resourceProgressEntity, resources, buildingProcesses, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="divisionCompetitionApp.resourceProgress.home.createOrEditLabel">Create or edit a ResourceProgress</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : resourceProgressEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="resource-progress-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="progressLabel" for="progress">
                    Progress
                  </Label>
                  <AvField id="resource-progress-progress" type="string" className="form-control" name="progress" />
                </AvGroup>
                <AvGroup>
                  <Label id="lastModifiedLabel" for="lastModified">
                    Last Modified
                  </Label>
                  <AvInput
                    id="resource-progress-lastModified"
                    type="datetime-local"
                    className="form-control"
                    name="lastModified"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.resourceProgressEntity.lastModified)}
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="resource.id">Resource</Label>
                  <AvInput id="resource-progress-resource" type="select" className="form-control" name="resource.id">
                    <option value="" key="0" />
                    {resources
                      ? resources.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="buildingProcess.id">Building Process</Label>
                  <AvInput id="resource-progress-buildingProcess" type="select" className="form-control" name="buildingProcess.id">
                    <option value="" key="0" />
                    {buildingProcesses
                      ? buildingProcesses.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/resource-progress" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  resources: storeState.resource.entities,
  buildingProcesses: storeState.buildingProcess.entities,
  resourceProgressEntity: storeState.resourceProgress.entity,
  loading: storeState.resourceProgress.loading,
  updating: storeState.resourceProgress.updating,
  updateSuccess: storeState.resourceProgress.updateSuccess
});

const mapDispatchToProps = {
  getResources,
  getBuildingProcesses,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ResourceProgressUpdate);
