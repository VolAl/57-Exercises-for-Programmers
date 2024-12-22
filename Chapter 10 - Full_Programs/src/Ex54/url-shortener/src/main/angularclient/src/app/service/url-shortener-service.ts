import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UrlShortenerService {

    private urlShortenerServiceUrl: string;

    constructor(private http: HttpClient) {
      this.urlShortenerServiceUrl = 'http://localhost:8080/url-shortener/shortener';
    }

    public postUrlShortener(longUrl: any): Observable<any> {
      const headers = { 'content-type': 'application/json'}
      const body = { 'urlId': 0, 'shortUrl': '', 'longUrl': longUrl, 'shortUrlStats': null };
      return this.http.post<any>(this.urlShortenerServiceUrl, body, {'headers':headers});
    }
}
