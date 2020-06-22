import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RentingApproveComponent } from './renting-approve.component';

describe('RentingApproveComponent', () => {
  let component: RentingApproveComponent;
  let fixture: ComponentFixture<RentingApproveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RentingApproveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RentingApproveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
