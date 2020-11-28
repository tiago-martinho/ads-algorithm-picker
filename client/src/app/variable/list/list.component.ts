import {Component, Input, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup} from '@angular/forms';
import {faPlus} from '@fortawesome/free-solid-svg-icons';
import {TypeOptions} from '../item/item.component';

@Component({
  selector: 'app-variable-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class VariableListComponent implements OnInit {
  iconPlus = faPlus;

  @Input() parent: FormGroup;

  variables: FormArray;

  public options = TypeOptions;
  selectedOption = TypeOptions.Integer;

  constructor() {
  }

  ngOnInit(): void {
    this.selectedOption = TypeOptions.Integer;
    this.variables = new FormArray([
      createNewVariable('Grain quality index', this.selectedOption),
      createNewVariable('Grain cost', this.selectedOption)
    ]);

    this.parent.addControl('type', new FormControl(this.selectedOption));
    this.parent.addControl('variables', this.variables);
  }

  addVariable(): void {
    console.log('Adding new variable');
    this.variables.push(createNewVariable(null, this.selectedOption));
  }

  removeVariable(index: number): void {
    console.log('Removing variable at index:', index);
    this.variables.removeAt(index);
  }
}

function createNewVariable(name, type): FormGroup {
  const val = getTypeValue(type);
  return new FormGroup({
    name: new FormControl(name),
    lowerLimit: new FormControl(val ? -val : null),
    upperLimit: new FormControl(val),
  });
}

function getTypeValue(type: TypeOptions): number {
  if (type === TypeOptions.Integer) {
    return 10;
  } else if (type === TypeOptions.Double) {
    return 0.5;
  } else {
    return null;
  }
}
