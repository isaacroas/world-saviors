import { DeedType } from '../domain/deed-type.domain';
import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController
} from '@angular/common/http/testing';
import { DeedService } from './deed.service';
import { Deed } from '../domain/deed.domain';

describe('DeedService', () => {

  let service: DeedService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DeedService],
      imports: [HttpClientTestingModule]
    });

    httpMock = TestBed.get(HttpTestingController);
    service = TestBed.get(DeedService);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get the list of deeds', () => {
    const deedList = [
      new Deed('foo'),
      new Deed('bar'),
      new Deed('baz')
    ];

    service.getDeeds()
      .subscribe(output => {
        expect(output.length).toEqual(3);
        expect(output[0].text).toEqual('foo');
        expect(output[1].text).toEqual('bar');
        expect(output[2].text).toEqual('baz');
      });

    const req = httpMock.expectOne('http://localhost:8080/world-saviors/api/deeds');
    expect(req.request.method).toEqual('GET');
    req.flush(deedList);
  });

  it('should add a deed', () => {
    const deed = new Deed('foo');

    service.putDeed(deed).subscribe(output => {
      expect(output.text).toEqual('foo');
    });
    const testRequest = httpMock.expectOne('http://localhost:8080/world-saviors/api/deeds');
    expect(testRequest.request.method).toEqual('PUT');
    testRequest.flush(deed);
  });

  it('should get the list of deed types', () => {
    const deedTypes = [
      new DeedType('0', 'foo'),
      new DeedType('1', 'bar'),
      new DeedType('2', 'baz'),
      new DeedType('3', 'qux')
    ];

    service.getDeedTypes()
      .subscribe(output => {
        expect(output.length).toEqual(4);
        expect(output[0].id).toEqual('0');
        expect(output[0].name).toEqual('foo');
        expect(output[1].id).toEqual('1');
        expect(output[1].name).toEqual('bar');
        expect(output[2].id).toEqual('2');
        expect(output[2].name).toEqual('baz');
        expect(output[3].id).toEqual('3');
        expect(output[3].name).toEqual('qux');
      });

    const testRequest = httpMock.expectOne('http://localhost:8080/world-saviors/api/deeds/types');
    expect(testRequest.request.method).toEqual('GET');
    testRequest.flush(deedTypes);
  });

});
