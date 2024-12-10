import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TimeService {

    private timeServiceUrl: string;

    constructor(private http: HttpClient) {
      this.timeServiceUrl = 'http://localhost:8080/time-service';
    }

    public getOwnTime(): Observable<any> {
      return this.http.get<any>(this.timeServiceUrl);
    }
}
