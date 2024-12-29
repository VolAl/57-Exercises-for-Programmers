import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { TextViewComponent } from './text-view/text-view.component';
import { TextSharingService } from './service/text.sharing.service';

@NgModule({
  declarations: [
    AppComponent,
    TextViewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [TextSharingService],
  bootstrap: [AppComponent]
})
export class AppModule { }
