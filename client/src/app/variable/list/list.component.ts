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
  iconPlus = faPlus;

  @Input() parent: FormGroup;

  variables: FormArray;

  public options = TypeOptions;
  selectedOption = TypeOptions.DOUBLE;

  constructor() {
  }

  ngOnInit(): void {
    const val = getTypeValue(this.selectedOption);
    this.variables = new FormArray([
      createNewVariable('Grain quality index', val ? -val : null, val),
      createNewVariable('Grain cost', val ? -val : null, val)
    ]);

    this.parent.addControl('type', new FormControl(this.selectedOption));
    this.parent.addControl('variables', this.variables);
  }

  addVariable(): void {
    console.log('Adding new variable');
    this.variables.push(createNewVariable(null, null, null));
  }

  removeVariable(index: number): void {
    console.log('Removing variable at index:', index);
    this.variables.removeAt(index);
  }

  getTypeName(type: string): string {
    switch (type) {
      case 'BINARY': return 'Binary';
      case 'DOUBLE': return 'Double';
      case 'INTEGER': return 'Integer';
      default:        return type;
    }
  }
}

function createNewVariable(name, from, to): FormGroup {
  return new FormGroup({
    name: new FormControl(name),
    lowerLimit: new FormControl(from),
    upperLimit: new FormControl(to),
  });
}

function getTypeValue(type: TypeOptions): number {
  if (type === TypeOptions.INTEGER) {
    return 10;
  } else if (type === TypeOptions.DOUBLE) {
    return 0.5;
  } else {
    return null;
  }
}
