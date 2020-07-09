import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StatisticsMostCommentedComponent } from './statistics-most-commented.component';

describe('StatisticsMostCommentedComponent', () => {
  let component: StatisticsMostCommentedComponent;
  let fixture: ComponentFixture<StatisticsMostCommentedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StatisticsMostCommentedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StatisticsMostCommentedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
