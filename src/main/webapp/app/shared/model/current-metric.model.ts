import { Moment } from 'moment';
import { ISubbranch } from 'app/shared/model/subbranch.model';
import { IMetric } from 'app/shared/model/metric.model';

export interface ICurrentMetric {
  id?: number;
  count?: number;
  finalizedCount?: number;
  finalizeDate?: Moment;
  lastModified?: Moment;
  subbranch?: ISubbranch;
  metric?: IMetric;
}

export const defaultValue: Readonly<ICurrentMetric> = {};
