import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { ApiService } from './http/api.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [ ApiService ],
})
export class AppComponent {
  title = 'client';

  form: FormGroup;
  currentStep: string;
  error: 'Unknown error';
  solution: {};

  constructor(private fb: FormBuilder, private heroesService: ApiService) {}

  ngOnInit() {
    this.currentStep = 'form';
    this.form = this.fb.group({
      description: '',
    });
  }

  onSubmit(form: FormGroup): void {
    this.currentStep = 'loading';

    this.heroesService.getSolution(this.form.value)
      .pipe(catchError(this.handleError))
      .subscribe(
        data => {
          console.log('Received solution:', data);
          this.currentStep = 'results';
          this.solution = data;
        },
        error => {
          console.error('Error fetching solution:', error);
          this.currentStep = 'error';
          this.error = error.message;
        }
      );
  }

  private handleError(error: HttpErrorResponse) {
    return throwError({
      code: error.status,
      message: error.error?.message ?? 'Unknown error'
    });
  }

  goBack(): void {
    this.currentStep = 'form';
  }
}
