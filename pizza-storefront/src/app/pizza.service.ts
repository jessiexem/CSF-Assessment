// Implement the methods in PizzaService for Task 3
// Add appropriate parameter and return type 

import { Order, OrderSummary } from "./models";
import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders} from "@angular/common/http";
import { firstValueFrom, map, Subject } from "rxjs";

@Injectable()
export class PizzaService {


  orderSummaryList: OrderSummary[] = new Array()

  constructor(private http: HttpClient) { }

  // POST /api/order
  // Add any required parameters or return type
  createOrder(/* add any required parameters */ orderDetails: Order) { 

    const headers = new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Accept','application/json')

    return firstValueFrom (
      this.http.post<any>('/api/order', orderDetails, {headers})
    )
  }

  // GET /api/order/<email>/all
  // Add any required parameters or return type
  getOrders(/* add any required parameters */ email: string) { 
    const headers = new HttpHeaders()
    .set('Content-Type', 'application/json')
    .set('Accept','application/json')

    return firstValueFrom(
      this.http.get<OrderSummary[]>(`/api/order/${email}/all`)
      .pipe(
        map((result) => {
            console.log("search term: ", email)
            this.orderSummaryList = result
            return result
          }
        )
      )
    )
  }

}
