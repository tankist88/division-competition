import { IResourceProgress } from 'app/shared/model/resource-progress.model';
import { ISubbranch } from 'app/shared/model/subbranch.model';
import { IBuilding } from 'app/shared/model/building.model';

export interface IBuildingProcess {
  id?: number;
  resources?: IResourceProgress[];
  subbranch?: ISubbranch;
  building?: IBuilding;
}

export const defaultValue: Readonly<IBuildingProcess> = {};
