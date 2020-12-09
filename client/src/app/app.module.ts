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
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CollapseModule } from 'ngx-bootstrap/collapse';
import { AlgorithmResultsComponent } from './algorithm-results/algorithm-results.component';
import { SolutionResultsComponent } from './solution-results/solution-results.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    VariableItemComponent,
    VariableListComponent,
    ObjectiveItemComponent,
    ObjectiveListComponent,
    AlgorithmResultsComponent,
    SolutionResultsComponent,
  ],
  imports: [
    BrowserModule,
    FontAwesomeModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    CollapseModule.forRoot(),
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
