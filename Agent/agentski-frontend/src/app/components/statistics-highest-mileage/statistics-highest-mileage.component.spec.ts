import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StatisticsHighestMileageComponent } from './statistics-highest-mileage.component';

describe('StatisticsHighestMileageComponent', () => {
  let component: StatisticsHighestMileageComponent;
  let fixture: ComponentFixture<StatisticsHighestMileageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StatisticsHighestMileageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StatisticsHighestMileageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
