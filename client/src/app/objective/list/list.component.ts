import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-objective-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ObjectiveListComponent implements OnInit {
  items: string[];

  constructor() {
    this.items = ['Cost', 'Quality'];
  }

  ngOnInit(): void {
  }

  addObjective(): void {
    console.log('Adding new objective');
    this.items.push('');
  }

  removeObjective(index: number): void {
    console.log('Removing objective at index:', index);
    this.items.splice(index, 1);
  }

}
