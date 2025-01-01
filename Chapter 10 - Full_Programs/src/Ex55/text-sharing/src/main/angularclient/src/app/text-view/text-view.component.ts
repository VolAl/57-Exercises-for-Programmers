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
  textDao: any = { textIdHash: '', text: '', textUrl: ''};

  constructor(private router: Router,
          public route: ActivatedRoute,
          private textSharingService: TextSharingService) {
      }

  ngOnInit() {
    this.textSharingService.getTextDao(this.hashedId).subscribe(response => {
      this.textDao = response;
      console.log(response);
    });
  }

  editText() {
      this.router.navigate(['', this.hashedId]);
    }
}
