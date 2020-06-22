import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CodebookMenagementComponent } from './codebook-menagement.component';

describe('CodebookMenagementComponent', () => {
  let component: CodebookMenagementComponent;
  let fixture: ComponentFixture<CodebookMenagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CodebookMenagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CodebookMenagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
