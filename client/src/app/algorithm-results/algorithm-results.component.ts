import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-algorithm-results',
  templateUrl: './algorithm-results.component.html',
  styleUrls: ['./algorithm-results.component.scss'],
})
export class AlgorithmResultsComponent implements OnInit {
  @Input() algorithmResults: string;

  constructor() {}

  ngOnInit(): void {}
}
