import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  form: FormGroup;

  constructor (private fb: FormBuilder) {}

  ngOnInit() {

    this.form = this.fb.group({
      description: '',

    })
 }

 onSubmit(form: FormGroup){
  console.log(JSON.stringify(this.form.value));
}

  title = 'client';
}
