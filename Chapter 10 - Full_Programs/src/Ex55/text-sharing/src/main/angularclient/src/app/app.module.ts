import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { TextViewComponent } from './text-view/text-view.component';
import { TextEditComponent } from './text-edit/text-edit.component';
import { TextSharingService } from './service/text.sharing.service';
import { EditorModule } from 'primeng/editor';

@NgModule({
  declarations: [
    AppComponent,
    TextViewComponent,
    TextEditComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
    EditorModule
  ],
  providers: [TextSharingService],
  bootstrap: [AppComponent]
})
export class AppModule { }
