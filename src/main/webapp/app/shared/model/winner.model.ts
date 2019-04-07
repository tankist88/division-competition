import { ISubbranch } from 'app/shared/model/subbranch.model';
import { IBuilding } from 'app/shared/model/building.model';

export interface IWinner {
  id?: number;
  subbranch?: ISubbranch;
  building?: IBuilding;
}

export const defaultValue: Readonly<IWinner> = {};
