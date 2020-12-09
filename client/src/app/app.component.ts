import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { ApiService } from './http/api.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [ApiService],
})
export class AppComponent implements OnInit {
  title = 'client';

  form: FormGroup;
  currentStep: string;
  error: 'Unknown error';
  solution: {};
  isCollapsed = false;
  showAlgorithm = true;
  showSolutions = false;

  constructor(private fb: FormBuilder, private apiService: ApiService) {}

  ngOnInit(): void {
    this.currentStep = 'form';
    this.form = this.fb.group({
      description: '',
    });
  }

  onSubmit(): void {
    this.currentStep = 'loading';

    this.apiService
      .getSolution(this.form.value)
      .pipe(catchError(this.handleError))
      .subscribe(
        (data) => {
          console.log('Received solution:', data);
          this.currentStep = 'results';
          this.solution = data;
        },
        (error) => {
          console.error('Error fetching solution:', error);
          this.currentStep = 'error';
          this.error = error.message;
        }
      );
  }

  updateCollapseState(button: string): void {
    if (button === 'algorithm') {
      this.showSolutions = false;
      this.showAlgorithm = !this.showAlgorithm;
    }
    if (button === 'solutions') {
      this.showSolutions = !this.showSolutions;
      this.showAlgorithm = false;
    }
    this.isCollapsed =
      !this.isCollapsed && !this.showAlgorithm && !this.showSolutions;
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    return throwError({
      code: error.status,
      message: error.error?.message ?? 'Unknown error',
    });
  }

  showFullResults(): void {
    this.currentStep = 'fullResults';
  }

  goBack(): void {
    if (this.currentStep === 'fullResults') {
      this.currentStep = 'results';
    } else {
      this.currentStep = 'form';
    }
  }
}
