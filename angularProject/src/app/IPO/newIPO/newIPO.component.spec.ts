/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { NewIPOComponent } from './newIPO.component';

describe('NewIPOComponent', () => {
  let component: NewIPOComponent;
  let fixture: ComponentFixture<NewIPOComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewIPOComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewIPOComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
