import { Component, Input, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup } from '@angular/forms';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import { TypeOptions } from '../item/item.component';

@Component({
  selector: 'app-variable-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class VariableListComponent implements OnInit {
  faPlus = faPlus

  @Input() parent: FormGroup;

  variables: FormArray;

  public options = TypeOptions;
  selectedOption =  TypeOptions.Integer

  constructor() {
  }

  ngOnInit(): void {
    this.variables = new FormArray([]);
    this.variables.push(createNewVariable('Grain quality index'))
    this.variables.push(createNewVariable('Grain quality index'))
    this.parent.addControl("type", new FormControl(this.selectedOption));
    this.parent.addControl("variables", this.variables);

  }

  addVariable(): void {
    console.log('Adding new variable');
    this.variables.push(createNewVariable())
  }

  removeVariable(index: number): void {
    console.log('Removing variable at index:', index);
    this.variables.removeAt(index)
  }

}

function createNewVariable(name= 'value'){
  return new FormGroup({
    name: new FormControl(name),
    lowerLimit: new FormControl(-0.1),
    upperLimit: new FormControl(0.1),
  });
}




