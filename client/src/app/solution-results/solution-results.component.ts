import { Component, Input, OnInit } from '@angular/core';
import { Solution } from '../models/solution.model';

@Component({
  selector: 'app-solution-results',
  templateUrl: './solution-results.component.html',
  styleUrls: ['./solution-results.component.scss'],
})
export class SolutionResultsComponent implements OnInit {
  @Input() solutionResults: Solution[];

  constructor() {}

  ngOnInit(): void {}
}
