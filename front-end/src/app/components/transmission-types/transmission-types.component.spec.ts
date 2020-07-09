import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransmissionTypesComponent } from './transmission-types.component';

describe('TransmissionTypesComponent', () => {
  let component: TransmissionTypesComponent;
  let fixture: ComponentFixture<TransmissionTypesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransmissionTypesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransmissionTypesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
