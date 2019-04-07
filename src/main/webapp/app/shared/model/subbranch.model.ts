export interface ISubbranch {
  id?: number;
  tb?: number;
  branch?: number;
  subbranch?: number;
  name?: string;
}

export const defaultValue: Readonly<ISubbranch> = {};
