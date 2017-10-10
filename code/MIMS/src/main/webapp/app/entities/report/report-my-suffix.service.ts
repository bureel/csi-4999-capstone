import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ReportMySuffix } from './report-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ReportMySuffixService {

    private resourceUrl = SERVER_API_URL + 'api/reports';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/reports';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(report: ReportMySuffix): Observable<ReportMySuffix> {
        const copy = this.convert(report);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(report: ReportMySuffix): Observable<ReportMySuffix> {
        const copy = this.convert(report);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ReportMySuffix> {
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

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
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
     * Convert a returned JSON object to ReportMySuffix.
     */
    private convertItemFromServer(json: any): ReportMySuffix {
        const entity: ReportMySuffix = Object.assign(new ReportMySuffix(), json);
        entity.dateOfBirth = this.dateUtils
            .convertDateTimeFromServer(json.dateOfBirth);
        entity.timeOfLastSeen = this.dateUtils
            .convertDateTimeFromServer(json.timeOfLastSeen);
        return entity;
    }

    /**
     * Convert a ReportMySuffix to a JSON which can be sent to the server.
     */
    private convert(report: ReportMySuffix): ReportMySuffix {
        const copy: ReportMySuffix = Object.assign({}, report);

        copy.dateOfBirth = this.dateUtils.toDate(report.dateOfBirth);

        copy.timeOfLastSeen = this.dateUtils.toDate(report.timeOfLastSeen);
        return copy;
    }
}
