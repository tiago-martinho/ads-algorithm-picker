import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { faPlus } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-variable-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class VariableListComponent implements OnInit {
  items: string[];
  faPlus = faPlus

  public options = TypeOptions;
  selectedOption =  TypeOptions.Double

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

export enum TypeOptions {
	Double = "Double" ,
	Integer = "Integer",
	Binary = "Binary"
}

