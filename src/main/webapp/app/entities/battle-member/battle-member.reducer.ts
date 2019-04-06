import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBattleMember, defaultValue } from 'app/shared/model/battle-member.model';

export const ACTION_TYPES = {
  FETCH_BATTLEMEMBER_LIST: 'battleMember/FETCH_BATTLEMEMBER_LIST',
  FETCH_BATTLEMEMBER: 'battleMember/FETCH_BATTLEMEMBER',
  CREATE_BATTLEMEMBER: 'battleMember/CREATE_BATTLEMEMBER',
  UPDATE_BATTLEMEMBER: 'battleMember/UPDATE_BATTLEMEMBER',
  DELETE_BATTLEMEMBER: 'battleMember/DELETE_BATTLEMEMBER',
  RESET: 'battleMember/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBattleMember>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type BattleMemberState = Readonly<typeof initialState>;

// Reducer

export default (state: BattleMemberState = initialState, action): BattleMemberState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BATTLEMEMBER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BATTLEMEMBER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BATTLEMEMBER):
    case REQUEST(ACTION_TYPES.UPDATE_BATTLEMEMBER):
    case REQUEST(ACTION_TYPES.DELETE_BATTLEMEMBER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BATTLEMEMBER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BATTLEMEMBER):
    case FAILURE(ACTION_TYPES.CREATE_BATTLEMEMBER):
    case FAILURE(ACTION_TYPES.UPDATE_BATTLEMEMBER):
    case FAILURE(ACTION_TYPES.DELETE_BATTLEMEMBER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BATTLEMEMBER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_BATTLEMEMBER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BATTLEMEMBER):
    case SUCCESS(ACTION_TYPES.UPDATE_BATTLEMEMBER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BATTLEMEMBER):
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

const apiUrl = 'api/battle-members';

// Actions

export const getEntities: ICrudGetAllAction<IBattleMember> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BATTLEMEMBER_LIST,
  payload: axios.get<IBattleMember>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IBattleMember> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BATTLEMEMBER,
    payload: axios.get<IBattleMember>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBattleMember> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BATTLEMEMBER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBattleMember> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BATTLEMEMBER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBattleMember> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BATTLEMEMBER,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
