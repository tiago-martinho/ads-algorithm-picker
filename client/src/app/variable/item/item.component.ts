import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { faTrash } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-variable-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.scss']
})
export class VariableItemComponent implements OnInit {

  faTrash=faTrash;
  options = TypeOptions;
  
  @Input() parent: FormGroup;

  @Input() selectedOption: TypeOptions; // tslint:disable-line:no-input-rename

  @Output() removed = new EventEmitter<string>();

  constructor() { 
  }

  ngOnInit(): void {
    
  }

  removeSelf(): void {
    this.removed.emit();
  }

}


export enum TypeOptions {
  Double = "Double" ,
  Binary = "Binary",
	Integer = "Integer",
}

