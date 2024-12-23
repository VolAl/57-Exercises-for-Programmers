import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UrlShortenerService } from './service/url-shortener-service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ReactiveFormsModule, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

  shortUrl: string = '';
  shortUrlStats: any = { longUrl: '', shortUrl: '', timesShortUrlHasBeenAccessed: ''};

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

  showShortUrlStats() {
    let urlIndex = this.shortUrl.charAt(this.shortUrl.length - 1) + '';
    this.urlShortenerService.shortUrlStats(urlIndex).subscribe(data => {
      this.shortUrlStats.longUrl = data.longUrl;
      this.shortUrlStats.shortUrl = data.shortUrl;
      this.shortUrlStats.timesShortUrlHasBeenAccessed = data.timesShortUrlHasBeenAccessed;
      console.log(data);
    });
  }
}
