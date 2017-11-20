import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Tip } from './tip.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TipService {

    private resourceUrl = SERVER_API_URL + 'api/tips';

    constructor(private http: Http) { }

    create(tip: Tip): Observable<Tip> {
        const copy = this.convert(tip);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(tip: Tip): Observable<Tip> {
        const copy = this.convert(tip);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Tip> {
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

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Tip.
     */
    private convertItemFromServer(json: any): Tip {
        const entity: Tip = Object.assign(new Tip(), json);
        return entity;
    }

    /**
     * Convert a Tip to a JSON which can be sent to the server.
     */
    private convert(tip: Tip): Tip {
        const copy: Tip = Object.assign({}, tip);
        return copy;
    }
}
