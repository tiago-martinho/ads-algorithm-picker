import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { ArgumentItemComponent } from './argument/item/item.component';
import { ArgumentListComponent } from './argument/list/list.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    ArgumentItemComponent,
    ArgumentListComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
