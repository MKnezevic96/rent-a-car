import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StatisticsTopRatedComponent } from './statistics-top-rated.component';

describe('StatisticsTopRatedComponent', () => {
  let component: StatisticsTopRatedComponent;
  let fixture: ComponentFixture<StatisticsTopRatedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StatisticsTopRatedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StatisticsTopRatedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
