import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BlockedUsersListComponent } from './blocked-users-list.component';

describe('BlockedUsersListComponent', () => {
  let component: BlockedUsersListComponent;
  let fixture: ComponentFixture<BlockedUsersListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BlockedUsersListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BlockedUsersListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
