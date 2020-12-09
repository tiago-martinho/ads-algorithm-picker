import { Component, Input, OnInit } from '@angular/core';
import { Inputs } from '../models/inputs.model';
import { Solution } from '../models/solution.model';

@Component({
  selector: 'app-solution-results',
  templateUrl: './solution-results.component.html',
  styleUrls: ['./solution-results.component.scss'],
})
export class SolutionResultsComponent implements OnInit {
  @Input() solutionResults: Solution[];
  @Input() inputs: Inputs;

  selectedSolution: Solution;

  selectableValues = ['Objective', 'Variable'];
  selectedValue: string;

  constructor() {}

  ngOnInit(): void {}
}
