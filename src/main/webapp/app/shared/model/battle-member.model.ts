import { Moment } from 'moment';
import { ISubbranch } from 'app/shared/model/subbranch.model';
import { IBattleType } from 'app/shared/model/battle-type.model';

export interface IBattleMember {
  id?: number;
  status?: number;
  lastModified?: Moment;
  subbranch?: ISubbranch;
  type?: IBattleType;
}

export const defaultValue: Readonly<IBattleMember> = {};
