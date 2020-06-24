/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { IPOComponent } from './IPO.component';

describe('IPOComponent', () => {
  let component: IPOComponent;
  let fixture: ComponentFixture<IPOComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IPOComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IPOComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
