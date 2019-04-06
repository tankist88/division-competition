import { Moment } from 'moment';
import { IResource } from 'app/shared/model/resource.model';
import { IBuildingProcess } from 'app/shared/model/building-process.model';

export interface IResourceProgress {
  id?: number;
  progress?: number;
  lastModified?: Moment;
  resource?: IResource;
  buildingProcess?: IBuildingProcess;
}

export const defaultValue: Readonly<IResourceProgress> = {};
