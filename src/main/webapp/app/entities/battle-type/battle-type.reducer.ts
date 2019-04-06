import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBattleType, defaultValue } from 'app/shared/model/battle-type.model';

export const ACTION_TYPES = {
  FETCH_BATTLETYPE_LIST: 'battleType/FETCH_BATTLETYPE_LIST',
  FETCH_BATTLETYPE: 'battleType/FETCH_BATTLETYPE',
  CREATE_BATTLETYPE: 'battleType/CREATE_BATTLETYPE',
  UPDATE_BATTLETYPE: 'battleType/UPDATE_BATTLETYPE',
  DELETE_BATTLETYPE: 'battleType/DELETE_BATTLETYPE',
  RESET: 'battleType/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBattleType>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type BattleTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: BattleTypeState = initialState, action): BattleTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BATTLETYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BATTLETYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BATTLETYPE):
    case REQUEST(ACTION_TYPES.UPDATE_BATTLETYPE):
    case REQUEST(ACTION_TYPES.DELETE_BATTLETYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BATTLETYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BATTLETYPE):
    case FAILURE(ACTION_TYPES.CREATE_BATTLETYPE):
    case FAILURE(ACTION_TYPES.UPDATE_BATTLETYPE):
    case FAILURE(ACTION_TYPES.DELETE_BATTLETYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BATTLETYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_BATTLETYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BATTLETYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_BATTLETYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BATTLETYPE):
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

const apiUrl = 'api/battle-types';

// Actions

export const getEntities: ICrudGetAllAction<IBattleType> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BATTLETYPE_LIST,
  payload: axios.get<IBattleType>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IBattleType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BATTLETYPE,
    payload: axios.get<IBattleType>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBattleType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BATTLETYPE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBattleType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BATTLETYPE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBattleType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BATTLETYPE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
