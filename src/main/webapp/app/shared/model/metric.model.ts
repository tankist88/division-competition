export const enum MetricTermType {
  FIXED_DAY_OF_MONTH = 'FIXED_DAY_OF_MONTH',
  N_WORK_DAY_OF_MONTH = 'N_WORK_DAY_OF_MONTH'
}

export interface IMetric {
  id?: number;
  metricId?: number;
  metricName?: string;
  termType?: MetricTermType;
  term?: number;
}

export const defaultValue: Readonly<IMetric> = {};
