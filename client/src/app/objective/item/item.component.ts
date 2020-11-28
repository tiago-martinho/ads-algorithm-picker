import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { AbstractControl, FormGroup } from '@angular/forms';
import { faTrash } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-objective-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.scss']
})
export class ObjectiveItemComponent implements OnInit {
  iconTrash = faTrash;

  public options = ObjectiveOptions;
  selectedOption;

  @Input() parentControl: AbstractControl;
  @Output() removed = new EventEmitter<string>();


  constructor() {
  }

  ngOnInit(): void {
    this.selectedOption = this.parentControl.value.goal;
  }

  removeSelf(): void {
    this.removed.emit();
  }

  getGoalName(goal: string): string {
    switch (goal) {
      case 'MINIMIZE': return 'Minimize';
      case 'MAXIMIZE': return 'Maximize';
      default:        return goal;
    }
  }
}

export enum ObjectiveOptions {
  MINIMIZE = 'MINIMIZE',
  MAXIMIZE = 'MAXIMIZE',
}

