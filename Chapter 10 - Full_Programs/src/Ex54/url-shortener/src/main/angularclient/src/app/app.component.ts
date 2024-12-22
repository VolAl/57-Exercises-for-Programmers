import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UrlShortenerService } from './service/url-shortener-service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ReactiveFormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

  shortUrl: string = '';

  urlForm = new FormGroup({
    longUrl: new FormControl('', Validators.required),
  });

  constructor(private urlShortenerService: UrlShortenerService) {}

  submit() {
    if (this.urlForm.invalid) {
      return;
    }

    let longUrl: string = '';
    if(this.urlForm.value.longUrl != null) {
      longUrl = this.urlForm.value.longUrl;

    }
    console.log(longUrl);

    this.urlShortenerService.postUrlShortener(longUrl).subscribe(data => {
      this.shortUrl = data.shortUrl;
      console.log(data);
    });
  }
}
