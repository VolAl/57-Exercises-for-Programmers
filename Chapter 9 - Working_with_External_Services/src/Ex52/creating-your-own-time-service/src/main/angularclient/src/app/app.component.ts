import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TimeService } from './service/time-service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {

  ownTime: string;

  constructor(private timeService: TimeService) {
    this.ownTime = '';
  }

  ngOnInit() {
    this.timeService.getOwnTime().subscribe(data => {
      let currentTime = data.currentTime;
      let date = currentTime.split(' ')[0];
      let time = currentTime.split(' ')[1];
      let year = date.split('-')[0];
      let month = date.split('-')[1];
      let monthNames = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
      let day = date.split('-')[2];
      this.ownTime = 'The current time is ' + time + ' UTC ' + monthNames[month - 1] + ' ' + day + ' ' + year + '.';

    });
  }
}
