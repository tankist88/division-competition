export interface IBattleType {
  id?: number;
  code?: string;
  name?: string;
  description?: string;
  termInMonths?: number;
}

export const defaultValue: Readonly<IBattleType> = {};
