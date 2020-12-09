import { CrossoverOperator } from './crossover-operator.model';
import { MutationOperator } from './mutation-operator.model';

export class AlgorithmResults {
  maxPopulationSize: number;
  selectionOperator: any;
  crossoverOperator: CrossoverOperator;
  mutationOperator: MutationOperator;
  name: string;
  description;
  string;
}
