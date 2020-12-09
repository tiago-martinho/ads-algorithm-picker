import { Objective } from './objective.model';
import { Options } from './options.model';
import { Variable } from './variable.model';

export class Inputs {
  description: string;
  objectives: Objective[];
  options: Options;
  type: string;
  variables: Variable[];
}
