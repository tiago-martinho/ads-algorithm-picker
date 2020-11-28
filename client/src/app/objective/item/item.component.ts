import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { faTrash } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-objective-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.scss']
})
export class ObjectiveItemComponent implements OnInit {
  @Output() removed = new EventEmitter<string>();

  faTrash=faTrash;
  public options = ObjectiveOptions;
  selectedOption =  ObjectiveOptions.Minimize

  @Input() parent: FormGroup;


  constructor() { }

  ngOnInit(): void {
  }

  removeSelf(): void {
    this.removed.emit();
  }

}

export enum ObjectiveOptions {
	Minimize = "Minimize" ,
	Maximize = "Maximize",
}

