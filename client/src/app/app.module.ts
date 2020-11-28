import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { VariableItemComponent } from './variable/item/item.component';
import { VariableListComponent } from './variable/list/list.component';
import { ObjectiveItemComponent } from './objective/item/item.component';
import { ObjectiveListComponent } from './objective/list/list.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    VariableItemComponent,
    VariableListComponent,
    ObjectiveItemComponent,
    ObjectiveListComponent,
  ],
  imports: [
    BrowserModule,
    FontAwesomeModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
