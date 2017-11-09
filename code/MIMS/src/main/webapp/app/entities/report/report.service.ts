import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Report } from './report.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ReportService {

    private resourceUrl = SERVER_API_URL + 'api/reports';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(report: Report): Observable<Report> {
        const copy = this.convert(report);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(report: Report): Observable<Report> {
        const copy = this.convert(report);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Report> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    queryForUser(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl + '/user', options)
            .map((res: Response) => this.convertResponse(res));
      }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Report.
     */
    private convertItemFromServer(json: any): Report {
        const entity: Report = Object.assign(new Report(), json);
        entity.dateOfBirth = this.dateUtils
            .convertLocalDateFromServer(json.dateOfBirth);
        entity.lastSeen = this.dateUtils
            .convertDateTimeFromServer(json.lastSeen);
        entity.createdAt = this.dateUtils
            .convertDateTimeFromServer(json.createdAt);
        entity.updatedAt = this.dateUtils
            .convertDateTimeFromServer(json.updatedAt);
        return entity;
    }

    /**
     * Convert a Report to a JSON which can be sent to the server.
     */
    private convert(report: Report): Report {
        const copy: Report = Object.assign({}, report);
        copy.dateOfBirth = this.dateUtils
            .convertLocalDateToServer(report.dateOfBirth);

        copy.lastSeen = this.dateUtils.toDate(report.lastSeen);

        copy.createdAt = this.dateUtils.toDate(report.createdAt);

        copy.updatedAt = this.dateUtils.toDate(report.updatedAt);
        return copy;
    }
}
