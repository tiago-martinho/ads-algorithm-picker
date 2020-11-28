import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  constructor(private fb: FormBuilder) {}

  form: FormGroup;
  currentStep: string;

  title = 'client';

  ngOnInit() {
    this.currentStep = 'form';
    this.form = this.fb.group({
      description: '',
    });
  }

  onSubmit(form: FormGroup) {
    this.currentStep = 'results';
    console.log('SUBMITTED', JSON.stringify(this.form.value));
  }

  goBack() {
    this.currentStep = 'form';
  }
}
