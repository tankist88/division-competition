import { IMetric } from 'app/shared/model/metric.model';
import { IBuilding } from 'app/shared/model/building.model';

export const enum ResourceType {
  NEGATIVE = 'NEGATIVE',
  POSITIVE = 'POSITIVE'
}

export interface IResource {
  id?: number;
  count?: number;
  type?: ResourceType;
  factor?: number;
  metric?: IMetric;
  building?: IBuilding;
}

export const defaultValue: Readonly<IResource> = {};
