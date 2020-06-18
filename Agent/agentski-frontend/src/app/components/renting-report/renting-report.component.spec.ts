import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RentingReportComponent } from './renting-report.component';

describe('RentingReportComponent', () => {
  let component: RentingReportComponent;
  let fixture: ComponentFixture<RentingReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RentingReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RentingReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
