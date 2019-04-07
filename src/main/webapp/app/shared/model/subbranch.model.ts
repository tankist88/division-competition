import { IWinner } from 'app/shared/model/winner.model';

export interface ISubbranch {
  id?: number;
  tb?: number;
  branch?: number;
  subbranch?: number;
  name?: string;
  winner?: IWinner;
}

export const defaultValue: Readonly<ISubbranch> = {};
