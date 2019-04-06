import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IResourceProgress, defaultValue } from 'app/shared/model/resource-progress.model';

export const ACTION_TYPES = {
  FETCH_RESOURCEPROGRESS_LIST: 'resourceProgress/FETCH_RESOURCEPROGRESS_LIST',
  FETCH_RESOURCEPROGRESS: 'resourceProgress/FETCH_RESOURCEPROGRESS',
  CREATE_RESOURCEPROGRESS: 'resourceProgress/CREATE_RESOURCEPROGRESS',
  UPDATE_RESOURCEPROGRESS: 'resourceProgress/UPDATE_RESOURCEPROGRESS',
  DELETE_RESOURCEPROGRESS: 'resourceProgress/DELETE_RESOURCEPROGRESS',
  RESET: 'resourceProgress/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IResourceProgress>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ResourceProgressState = Readonly<typeof initialState>;

// Reducer

export default (state: ResourceProgressState = initialState, action): ResourceProgressState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RESOURCEPROGRESS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RESOURCEPROGRESS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_RESOURCEPROGRESS):
    case REQUEST(ACTION_TYPES.UPDATE_RESOURCEPROGRESS):
    case REQUEST(ACTION_TYPES.DELETE_RESOURCEPROGRESS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_RESOURCEPROGRESS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RESOURCEPROGRESS):
    case FAILURE(ACTION_TYPES.CREATE_RESOURCEPROGRESS):
    case FAILURE(ACTION_TYPES.UPDATE_RESOURCEPROGRESS):
    case FAILURE(ACTION_TYPES.DELETE_RESOURCEPROGRESS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_RESOURCEPROGRESS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_RESOURCEPROGRESS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_RESOURCEPROGRESS):
    case SUCCESS(ACTION_TYPES.UPDATE_RESOURCEPROGRESS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_RESOURCEPROGRESS):
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

const apiUrl = 'api/resource-progresses';

// Actions

export const getEntities: ICrudGetAllAction<IResourceProgress> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_RESOURCEPROGRESS_LIST,
  payload: axios.get<IResourceProgress>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IResourceProgress> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RESOURCEPROGRESS,
    payload: axios.get<IResourceProgress>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IResourceProgress> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RESOURCEPROGRESS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IResourceProgress> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RESOURCEPROGRESS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IResourceProgress> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RESOURCEPROGRESS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
