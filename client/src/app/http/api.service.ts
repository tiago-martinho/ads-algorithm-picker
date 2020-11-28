import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
  })
};

@Injectable()
export class ApiService {
  constructor(private http: HttpClient) { }

  getSolution(data): Observable<{}> {
    console.log('Fetching solution for parameters:', data);
    return this.http.post(environment.apiUrl + '/', data, httpOptions);
  }
}
