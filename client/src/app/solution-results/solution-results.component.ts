import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-solution-results',
  templateUrl: './solution-results.component.html',
  styleUrls: ['./solution-results.component.scss'],
})
export class SolutionResultsComponent implements OnInit {
  @Input() solutionResults: string;

  constructor() {}

  ngOnInit(): void {}
}
