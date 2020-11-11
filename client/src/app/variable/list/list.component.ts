import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-variable-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class VariableListComponent implements OnInit {
  items: string[];

  constructor() {
    this.items = ['Grain quality index', 'Grain cost'];
  }

  ngOnInit(): void {
  }

  addVariable(): void {
    console.log('Adding new variable');
    this.items.push('');
  }

  removeVariable(index: number): void {
    console.log('Removing variable at index:', index);
    this.items.splice(index, 1);
  }

}
