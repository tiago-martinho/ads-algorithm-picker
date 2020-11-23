import { ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup } from '@angular/forms';
import { faPlus } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-variable-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class VariableListComponent implements OnInit {
  items: string[];
  faPlus = faPlus

  @Input() parent: FormGroup;

  variables: FormArray;

  public options = TypeOptions;
  selectedOption =  TypeOptions.Integer

  constructor(protected changeDetectorRef: ChangeDetectorRef) {
    this.items = ['Grain quality index', 'Grain cost'];
  }

  ngOnInit(): void {
    this.variables = new FormArray([]);
    this.parent.addControl("problemType", new FormControl(this.selectedOption));
    this.parent.addControl("variables", this.variables);

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
  Binary = "Binary",
	Integer = "Integer",
}

