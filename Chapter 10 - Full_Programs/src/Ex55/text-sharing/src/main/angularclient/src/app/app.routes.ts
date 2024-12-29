import { Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { TextEditComponent } from './text-edit/text-edit.component';
import { TextViewComponent } from './text-view/text-view.component';

export const routes: Routes = [
  {path: '', component: TextEditComponent},
  {path: ':hashedId', component: TextEditComponent},
  {path: 'text-view/:hashedId', component: TextViewComponent}
  ];
