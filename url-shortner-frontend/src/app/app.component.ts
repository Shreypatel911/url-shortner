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
  shortenedUrlHash : string;
  isAliasValid : boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private urlShortenerService: UrlShortenerService
  ) {}

  ngOnInit() {
    this.urlForm = this.formBuilder.group({
      longUrl : ['', [Validators.required, Validators.pattern(/^https?:\/\//)]],
      shortUrl : [null],
      alias : ['']
    });

    this.urlForm.get('shortUrl').disable();
  }

  shortenUrl() {
    if (this.urlForm.valid) {
      const longUrl = this.urlForm.get('longUrl').value;
      const alias  = this.urlForm.get('alias').value;
      this.urlShortenerService.shortenUrl(longUrl, alias).subscribe(
        (reponse) => {
          if(reponse['status'] === undefined){
            this.urlForm.get('shortUrl').setValue('http://localhost:8080/' + reponse['shortUrl']);
            this.shortenedUrlHash = reponse['shortUrl'];
          }else if(reponse['status'] === "400"){
            this.isAliasValid = true;
            console.log(this.isAliasValid);
          }
        },
        (error) => {
          console.error('Error shortening URL:', error);
        }
      );
    }
  }

  openShorterUrl() {
    this.urlShortenerService.openShorterUrl(this.shortenedUrlHash).subscribe(
      (response) => {
      
      },
      (error) => {
        console.error('Error shortening URL:', error);
      })
  }

  openLink(){

  }
}