import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBuildingProcess, defaultValue } from 'app/shared/model/building-process.model';

export const ACTION_TYPES = {
  FETCH_BUILDINGPROCESS_LIST: 'buildingProcess/FETCH_BUILDINGPROCESS_LIST',
  FETCH_BUILDINGPROCESS: 'buildingProcess/FETCH_BUILDINGPROCESS',
  CREATE_BUILDINGPROCESS: 'buildingProcess/CREATE_BUILDINGPROCESS',
  UPDATE_BUILDINGPROCESS: 'buildingProcess/UPDATE_BUILDINGPROCESS',
  DELETE_BUILDINGPROCESS: 'buildingProcess/DELETE_BUILDINGPROCESS',
  RESET: 'buildingProcess/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBuildingProcess>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type BuildingProcessState = Readonly<typeof initialState>;

// Reducer

export default (state: BuildingProcessState = initialState, action): BuildingProcessState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BUILDINGPROCESS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BUILDINGPROCESS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BUILDINGPROCESS):
    case REQUEST(ACTION_TYPES.UPDATE_BUILDINGPROCESS):
    case REQUEST(ACTION_TYPES.DELETE_BUILDINGPROCESS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BUILDINGPROCESS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BUILDINGPROCESS):
    case FAILURE(ACTION_TYPES.CREATE_BUILDINGPROCESS):
    case FAILURE(ACTION_TYPES.UPDATE_BUILDINGPROCESS):
    case FAILURE(ACTION_TYPES.DELETE_BUILDINGPROCESS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BUILDINGPROCESS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_BUILDINGPROCESS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BUILDINGPROCESS):
    case SUCCESS(ACTION_TYPES.UPDATE_BUILDINGPROCESS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BUILDINGPROCESS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/building-processes';

// Actions

export const getEntities: ICrudGetAllAction<IBuildingProcess> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BUILDINGPROCESS_LIST,
  payload: axios.get<IBuildingProcess>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IBuildingProcess> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BUILDINGPROCESS,
    payload: axios.get<IBuildingProcess>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBuildingProcess> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BUILDINGPROCESS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBuildingProcess> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BUILDINGPROCESS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBuildingProcess> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BUILDINGPROCESS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
