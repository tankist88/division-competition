import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICurrentMetric, defaultValue } from 'app/shared/model/current-metric.model';

export const ACTION_TYPES = {
  FETCH_CURRENTMETRIC_LIST: 'currentMetric/FETCH_CURRENTMETRIC_LIST',
  FETCH_CURRENTMETRIC: 'currentMetric/FETCH_CURRENTMETRIC',
  CREATE_CURRENTMETRIC: 'currentMetric/CREATE_CURRENTMETRIC',
  UPDATE_CURRENTMETRIC: 'currentMetric/UPDATE_CURRENTMETRIC',
  DELETE_CURRENTMETRIC: 'currentMetric/DELETE_CURRENTMETRIC',
  RESET: 'currentMetric/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICurrentMetric>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type CurrentMetricState = Readonly<typeof initialState>;

// Reducer

export default (state: CurrentMetricState = initialState, action): CurrentMetricState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CURRENTMETRIC_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CURRENTMETRIC):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CURRENTMETRIC):
    case REQUEST(ACTION_TYPES.UPDATE_CURRENTMETRIC):
    case REQUEST(ACTION_TYPES.DELETE_CURRENTMETRIC):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CURRENTMETRIC_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CURRENTMETRIC):
    case FAILURE(ACTION_TYPES.CREATE_CURRENTMETRIC):
    case FAILURE(ACTION_TYPES.UPDATE_CURRENTMETRIC):
    case FAILURE(ACTION_TYPES.DELETE_CURRENTMETRIC):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CURRENTMETRIC_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CURRENTMETRIC):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CURRENTMETRIC):
    case SUCCESS(ACTION_TYPES.UPDATE_CURRENTMETRIC):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CURRENTMETRIC):
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

const apiUrl = 'api/current-metrics';

// Actions

export const getEntities: ICrudGetAllAction<ICurrentMetric> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CURRENTMETRIC_LIST,
  payload: axios.get<ICurrentMetric>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ICurrentMetric> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CURRENTMETRIC,
    payload: axios.get<ICurrentMetric>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICurrentMetric> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CURRENTMETRIC,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICurrentMetric> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CURRENTMETRIC,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICurrentMetric> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CURRENTMETRIC,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
