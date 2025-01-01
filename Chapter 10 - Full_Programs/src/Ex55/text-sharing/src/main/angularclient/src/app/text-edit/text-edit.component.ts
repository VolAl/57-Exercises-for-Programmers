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

  textDao: any = { textIdHash: '', textList: [], textUrl: ''};
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
        this.textValue = this.textDao.textList[this.textDao.textList.length-1].textContent;
        this.textForm.patchValue({
          text: this.textValue
        });
        this.textUrl = this.textDao.textUrl;
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
      if(this.textDao.textList.length <= 0) {
        this.textDao.textList = [];
      }
      this.textDao.textList.push({ textId: this.textDao.textList.length, textContent: this.textForm.value.text });
    }
    console.log(this.textValue);

    if(this.hashedId != undefined && this.hashedId != '') {
      this.textDao.textIdHash = this.hashedId;
    } else {
      this.textDao.textIdHash = '';
    }

    this.textSharingService.saveOrUpdateText(this.textDao).subscribe(data => {
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
