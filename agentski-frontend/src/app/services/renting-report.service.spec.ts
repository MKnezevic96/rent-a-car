import { TestBed } from '@angular/core/testing';

import { RentingReportService } from './renting-report.service';

describe('RentingReportService', () => {
  let service: RentingReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RentingReportService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
