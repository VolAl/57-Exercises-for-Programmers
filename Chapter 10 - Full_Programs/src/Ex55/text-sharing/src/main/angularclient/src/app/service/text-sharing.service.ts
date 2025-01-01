import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TextSharingService {

  private textSharingServiceUrl: string;

  constructor(private http: HttpClient) {
    this.textSharingServiceUrl = 'http://localhost:8080/text-sharing/';
  }

  public saveOrUpdateText(textDao: any): Observable<any> {
    const headers = { 'content-type': 'application/json'}
    const body = { 'textIdHash': textDao.textIdHash, 'textList': textDao.textList, 'textUrl': textDao.textUrl };
    return this.http.post<any>(this.textSharingServiceUrl + "save", body, {'headers':headers});
  }

  public getTextDao(textIndex: string): Observable<any> {
    return this.http.get(this.textSharingServiceUrl + textIndex);
  }
}
