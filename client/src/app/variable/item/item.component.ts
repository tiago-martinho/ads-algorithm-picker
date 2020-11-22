import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup } from '@angular/forms';
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
  @Input() selectedOption: TypeOptions; // tslint:disable-line:no-input-rename

  @Input() name: string; // tslint:disable-line:no-input-rename
  @Output() removed = new EventEmitter<string>();

  constructor() { 
  }

  ngOnInit(): void {

  }

  removeSelf(): void {
    this.removed.emit();
  }

}

