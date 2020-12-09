import { Component, Input, OnInit } from '@angular/core';
import { AlgorithmResults } from '../models/algorithm-results.model';

@Component({
  selector: 'app-algorithm-results',
  templateUrl: './algorithm-results.component.html',
  styleUrls: ['./algorithm-results.component.scss'],
})
export class AlgorithmResultsComponent implements OnInit {
  @Input() algorithmResults: AlgorithmResults;

  constructor() {}

  ngOnInit(): void {}
}
