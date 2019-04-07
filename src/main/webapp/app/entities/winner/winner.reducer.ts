import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IWinner, defaultValue } from 'app/shared/model/winner.model';

export const ACTION_TYPES = {
  FETCH_WINNER_LIST: 'winner/FETCH_WINNER_LIST',
  FETCH_WINNER: 'winner/FETCH_WINNER',
  CREATE_WINNER: 'winner/CREATE_WINNER',
  UPDATE_WINNER: 'winner/UPDATE_WINNER',
  DELETE_WINNER: 'winner/DELETE_WINNER',
  RESET: 'winner/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IWinner>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type WinnerState = Readonly<typeof initialState>;

// Reducer

export default (state: WinnerState = initialState, action): WinnerState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_WINNER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_WINNER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_WINNER):
    case REQUEST(ACTION_TYPES.UPDATE_WINNER):
    case REQUEST(ACTION_TYPES.DELETE_WINNER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_WINNER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_WINNER):
    case FAILURE(ACTION_TYPES.CREATE_WINNER):
    case FAILURE(ACTION_TYPES.UPDATE_WINNER):
    case FAILURE(ACTION_TYPES.DELETE_WINNER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_WINNER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_WINNER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_WINNER):
    case SUCCESS(ACTION_TYPES.UPDATE_WINNER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_WINNER):
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

const apiUrl = 'api/winners';

// Actions

export const getEntities: ICrudGetAllAction<IWinner> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_WINNER_LIST,
  payload: axios.get<IWinner>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IWinner> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_WINNER,
    payload: axios.get<IWinner>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IWinner> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_WINNER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IWinner> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_WINNER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IWinner> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_WINNER,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
