import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-variable-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.scss']
})
export class VariableItemComponent implements OnInit {
  @Input('name') name: string; // tslint:disable-line:no-input-rename
  @Output() removed = new EventEmitter<string>();

  constructor() { }

  ngOnInit(): void {
  }

  removeSelf(): void {
    this.removed.emit();
  }

}
