import { Component } from '@angular/core';

@Component({
    moduleId: module.id,
    selector: 'app',
    templateUrl: 'app.component.html',
    styleUrls: [
        'app.component.css',
        ]
        
})

export class AppComponent { 
    isIn = false; 
    toggleState() {
        let bool = this.isIn;
        this.isIn = bool === false ? true : false; 
    }
}