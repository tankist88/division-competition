import { IResource } from 'app/shared/model/resource.model';

export interface IBuilding {
  id?: number;
  name?: string;
  description?: string;
  siteUrl?: string;
  pictureFile?: string;
  resources?: IResource[];
}

export const defaultValue: Readonly<IBuilding> = {};
