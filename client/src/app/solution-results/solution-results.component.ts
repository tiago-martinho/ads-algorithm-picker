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

  selectableValues = ['Objectives', 'Variables'];
  selectedValue: string;

  constructor() {}

  ngOnInit(): void {
    // Select the first solution by default
    this.selectedSolution = this.solutionResults[0];
    this.selectedValue = this.selectableValues[0];
  }
}
