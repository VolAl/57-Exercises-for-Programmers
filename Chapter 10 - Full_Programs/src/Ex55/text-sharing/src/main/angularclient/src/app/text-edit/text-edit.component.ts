import { Component, OnInit, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TextSharingService } from '../service/text-sharing.service';

@Component({
  selector: 'app-text-edit',
  imports: [RouterModule, ReactiveFormsModule, CommonModule],
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

  constructor(private textSharingService: TextSharingService) {
    }

  ngOnInit() {
    if(this.hashedId != undefined && this.hashedId != '') {
      this.textSharingService.getText(this.hashedId).subscribe(response => {
        this.textValue = response.text;
        this.textDao.text = response.text;
        this.textForm.patchValue({
          text: response.text
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

}
