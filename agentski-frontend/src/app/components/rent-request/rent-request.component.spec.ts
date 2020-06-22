import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RentRequestComponent } from './rent-request.component';

describe('RentRequestComponent', () => {
  let component: RentRequestComponent;
  let fixture: ComponentFixture<RentRequestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RentRequestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RentRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
