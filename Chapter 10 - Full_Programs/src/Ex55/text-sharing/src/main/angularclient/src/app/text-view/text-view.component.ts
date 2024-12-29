import { Component, Input, OnInit } from '@angular/core';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { TextSharingService } from '../service/text-sharing.service';

@Component({
  selector: 'app-text-view',
  imports: [RouterModule],
  templateUrl: './text-view.component.html',
  styleUrl: './text-view.component.css'
})
export class TextViewComponent implements OnInit {

  @Input()
  hashedId: string = '';
  textValue: string = '';

  constructor(private router: Router,
          public route: ActivatedRoute,
          private textSharingService: TextSharingService) {
      }

  ngOnInit() {
    this.textSharingService.getText(this.hashedId).subscribe(response => {
      this.textValue = response.text;
      console.log(response);
    });
  }

  editText() {
      this.router.navigate(['', this.hashedId]);
    }
}
