import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderSummary } from '../models';
import { PizzaService } from '../pizza.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  email!: string
  orderSummaries!: OrderSummary[]

  constructor(private pizzaSvc: PizzaService, private ar : ActivatedRoute) { }

  ngOnInit(): void {
    const email = this.ar.snapshot.params['email']
    this.email = email
    this.pizzaSvc.getOrders(email)
    .then(
      result => {
        console.info('>>>> getAllOrders result:', result)
        this.orderSummaries = result
      }
    )
    .catch(
      error=> {
        console.error(">>>> performGetList error: ", error)
        alert(`>>> performGetList error: ${JSON.stringify(error)}`)
      }
    )
  }

}
