import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RentingMenagementComponent } from './renting-menagement.component';

describe('RentingMenagementComponent', () => {
  let component: RentingMenagementComponent;
  let fixture: ComponentFixture<RentingMenagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RentingMenagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RentingMenagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
