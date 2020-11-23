import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { faTrash } from '@fortawesome/free-solid-svg-icons';
import { TypeOptions } from '../list/list.component';

@Component({
  selector: 'app-variable-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.scss']
})
export class VariableItemComponent implements OnInit {

  faTrash=faTrash;
  options = TypeOptions;
  
  @Input() parent: FormArray;

  parentForm : FormGroup;

  @Input() selectedOption: TypeOptions; // tslint:disable-line:no-input-rename

  @Input() name: string; // tslint:disable-line:no-input-rename
  @Output() removed = new EventEmitter<string>();

  constructor() { 
  }

  ngOnInit(): void {
    // console.log(name)
    this.parentForm =  new FormGroup({})
    this.parentForm.addControl("name", new FormControl(name));
    this.parentForm.addControl("lowerLimit", new FormControl());
    this.parentForm.addControl("upperLimit", new FormControl());
    this.parent.push(this.parentForm)


  }

  removeSelf(): void {
    this.removed.emit();
  }

}

