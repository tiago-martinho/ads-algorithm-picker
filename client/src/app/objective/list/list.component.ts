import { Component, Input, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup } from '@angular/forms';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import { ObjectiveOptions } from '../item/item.component';

@Component({
  selector: 'app-objective-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ObjectiveListComponent implements OnInit {
  items: string[];
  faPlus = faPlus

  @Input() parent: FormGroup;

  objectives: FormArray;


  constructor() {
  }

  ngOnInit(): void {
    this.objectives = new FormArray([]);
    this.objectives.push(createNewObjective('Cost'))
    this.objectives.push(createNewObjective('Quality'))
    this.parent.addControl("objectives", this.objectives);
  }

  addObjective(): void {
    console.log('Adding new objective');
    this.objectives.push(createNewObjective())

  }

  removeObjective(index: number): void {
    console.log('Removing objective at index:', index);
    this.objectives.removeAt(index)
  }
}

function createNewObjective(name= 'objective'){
  return new FormGroup({
    name: new FormControl(name),
    goal: new FormControl(ObjectiveOptions.Minimize),
  });
}

