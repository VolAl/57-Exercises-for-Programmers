import { Component, OnInit, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TextSharingService } from '../service/text-sharing.service';
import { EditorModule } from 'primeng/editor';

@Component({
  selector: 'app-text-edit',
  imports: [RouterModule, ReactiveFormsModule, CommonModule, EditorModule],
  templateUrl: './text-edit.component.html',
  styleUrl: './text-edit.component.css'
})
export class TextEditComponent implements OnInit {

  textDao: any = { textIdHash: '', text: '', textUrl: ''};
  textValue: string = '';
  textUrl: string = '';
  @Input()
  hashedId: string = '';

  textForm = new FormGroup({
    text: new FormControl('', Validators.required),
  });

  constructor(private router: Router, private textSharingService: TextSharingService) {
    }

  ngOnInit() {
    if(this.hashedId != undefined && this.hashedId != '') {
      this.textSharingService.getTextDao(this.hashedId).subscribe(response => {
        this.textDao = response;
        this.textValue = this.textDao.text;
        this.textForm.patchValue({
          text: this.textValue
        });
        console.log(response);
      });
    }
  }

  submit() {
    if (this.textForm.invalid) {
      return;
    }

    if(this.textForm.value.text != null) {
      this.textValue = this.textForm.value.text;
      this.textDao.text = this.textForm.value.text;
    }
    console.log(this.textValue);

    this.textSharingService.saveText(this.textDao).subscribe(data => {
          this.textUrl = data.textUrl;
          this.textDao.textUrl = data.textUrl;
          let textUrlParts = data.textUrl.split('/');
          this.hashedId = textUrlParts[textUrlParts.length - 1];
          console.log(data);
        });
  }

  onUrlClick(event :Event){
      event.preventDefault();
      this.router.navigate(['text-view', this.hashedId]);
  }

}
