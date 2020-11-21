import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { faTrash } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-variable-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.scss']
})
export class VariableItemComponent implements OnInit {

  public options = TypeOptions;
  faTrash=faTrash;
  selectedOption =  TypeOptions.Double

  @Input() name: string; // tslint:disable-line:no-input-rename
  @Output() removed = new EventEmitter<string>();

  constructor() { }

  ngOnInit(): void {
  }

  removeSelf(): void {
    this.removed.emit();
  }

}

export enum TypeOptions {
	Double = "Double" ,
	Integer = "Integer",
	Binary = "Binary"
}
