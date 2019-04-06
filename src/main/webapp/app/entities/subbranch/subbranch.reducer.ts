import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISubbranch, defaultValue } from 'app/shared/model/subbranch.model';

export const ACTION_TYPES = {
  FETCH_SUBBRANCH_LIST: 'subbranch/FETCH_SUBBRANCH_LIST',
  FETCH_SUBBRANCH: 'subbranch/FETCH_SUBBRANCH',
  CREATE_SUBBRANCH: 'subbranch/CREATE_SUBBRANCH',
  UPDATE_SUBBRANCH: 'subbranch/UPDATE_SUBBRANCH',
  DELETE_SUBBRANCH: 'subbranch/DELETE_SUBBRANCH',
  RESET: 'subbranch/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISubbranch>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type SubbranchState = Readonly<typeof initialState>;

// Reducer

export default (state: SubbranchState = initialState, action): SubbranchState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SUBBRANCH_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SUBBRANCH):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SUBBRANCH):
    case REQUEST(ACTION_TYPES.UPDATE_SUBBRANCH):
    case REQUEST(ACTION_TYPES.DELETE_SUBBRANCH):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_SUBBRANCH_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SUBBRANCH):
    case FAILURE(ACTION_TYPES.CREATE_SUBBRANCH):
    case FAILURE(ACTION_TYPES.UPDATE_SUBBRANCH):
    case FAILURE(ACTION_TYPES.DELETE_SUBBRANCH):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_SUBBRANCH_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SUBBRANCH):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SUBBRANCH):
    case SUCCESS(ACTION_TYPES.UPDATE_SUBBRANCH):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SUBBRANCH):
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

const apiUrl = 'api/subbranches';

// Actions

export const getEntities: ICrudGetAllAction<ISubbranch> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SUBBRANCH_LIST,
  payload: axios.get<ISubbranch>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ISubbranch> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SUBBRANCH,
    payload: axios.get<ISubbranch>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ISubbranch> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SUBBRANCH,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISubbranch> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SUBBRANCH,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISubbranch> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SUBBRANCH,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
