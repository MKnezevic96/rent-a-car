import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentMenagementComponent } from './comment-menagement.component';

describe('CommentMenagementComponent', () => {
  let component: CommentMenagementComponent;
  let fixture: ComponentFixture<CommentMenagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommentMenagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommentMenagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
