import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { VictimPhoto } from './victim-photo.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class VictimPhotoService {

    private resourceUrl = SERVER_API_URL + 'api/victim-photos';

    constructor(private http: Http) { }

    create(victimPhoto: VictimPhoto): Observable<VictimPhoto> {
        const copy = this.convert(victimPhoto);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(victimPhoto: VictimPhoto): Observable<VictimPhoto> {
        const copy = this.convert(victimPhoto);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<VictimPhoto> {
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
     * Convert a returned JSON object to VictimPhoto.
     */
    private convertItemFromServer(json: any): VictimPhoto {
        const entity: VictimPhoto = Object.assign(new VictimPhoto(), json);
        return entity;
    }

    /**
     * Convert a VictimPhoto to a JSON which can be sent to the server.
     */
    private convert(victimPhoto: VictimPhoto): VictimPhoto {
        const copy: VictimPhoto = Object.assign({}, victimPhoto);
        return copy;
    }
}
