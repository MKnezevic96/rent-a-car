import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserMenagementComponent } from './user-menagement.component';

describe('UserMenagementComponent', () => {
  let component: UserMenagementComponent;
  let fixture: ComponentFixture<UserMenagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserMenagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserMenagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
