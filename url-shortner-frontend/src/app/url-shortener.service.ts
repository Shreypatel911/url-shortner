import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UrlShortenerService {
  
  private apiUrl = 'https://springapi-66gap7pnaa-uc.a.run.app';
  // private apiUrl = 'http://localhost:8080';

  constructor(private http : HttpClient) { }

  shortenUrl(url: string, alias : string): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/generate`, { url, alias });
  }
}
