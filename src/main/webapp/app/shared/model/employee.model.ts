import { Moment } from 'moment';
import { ISubbranch } from 'app/shared/model/subbranch.model';

export interface IEmployee {
  id?: number;
  login?: string;
  firstName?: string;
  lastName?: string;
  secondName?: string;
  birthDay?: Moment;
  subbranch?: ISubbranch;
}

export const defaultValue: Readonly<IEmployee> = {};
