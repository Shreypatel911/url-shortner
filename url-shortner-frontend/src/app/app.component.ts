import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UrlShortenerService } from './url-shortener.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent {
  urlForm: FormGroup;
  shortenedUrl: string;
  shortenedUrlHash : string;

  constructor(
    private formBuilder: FormBuilder,
    private urlShortenerService: UrlShortenerService
  ) {}

  ngOnInit() {
    this.urlForm = this.formBuilder.group({
      longUrl: ['', [Validators.required, Validators.pattern(/^https?:\/\//)]],
    });
  }

  shortenUrl() {
    if (this.urlForm.valid) {
      const longUrl = this.urlForm.get('longUrl').value;
      this.urlShortenerService.shortenUrl(longUrl).subscribe(
        (reponse) => {
          console.log(reponse);
          this.shortenedUrl = 'http://localhost:8080/' + reponse['shortUrl'];
          this.shortenedUrlHash = reponse['shortUrl'];
        },
        (error) => {
          console.error('Error shortening URL:', error);
        }
      );
    }
  }

  openShorterUrl() {
    console.log("on click");
    this.urlShortenerService.openShorterUrl(this.shortenedUrlHash).subscribe(
      (response) => {
      
      },
      (error) => {
        console.error('Error shortening URL:', error);
      })
  }

}
