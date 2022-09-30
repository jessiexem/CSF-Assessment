import { outputAst } from '@angular/compiler';
import { Component, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Subject } from 'rxjs';
import { Order } from '../models';
import { PizzaService } from '../pizza.service';

const SIZES: string[] = [
  "Personal - 6 inches",
  "Regular - 9 inches",
  "Large - 12 inches",
  "Extra Large - 15 inches"
]

const PizzaToppings: string[] = [
    'chicken', 'seafood', 'beef', 'vegetables',
    'cheese', 'arugula', 'pineapple'
]

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  orderForm!: FormGroup
  email!: string


  pizzaSize = SIZES[0]

  constructor(private fb: FormBuilder, private pizzaSvc: PizzaService) {
    this.orderForm = this.fb.group ({
      name: this.fb.control<string>('', [Validators.required]),
      email: this.fb.control<string>('', [Validators.required, Validators.email]),
      size: this.fb.control<number>(0, [Validators.required]),
      base: this.fb.control<string>('', [Validators.required]),
      sauce: this.fb.control<string>('', [Validators.required]),
      toppings: this.fb.array([],[Validators.required]),
      //toppings: this.fb.control<string>('', [Validators.required]),
      comments: this.fb.control<string>('')
    })

    //this.addCheckboxes()
  }

  ngOnInit(): void {
    //this.orderForm = this.createForm()
  }

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)]
  }



  processCreateOrder() {
    const order = this.orderForm.value as Order 
    
    const selectedOrderIds = this.orderForm.value['toppings']
      .map((checked: any, i: number) => checked ? PizzaToppings[i] : null)
      .filter((v: any) => v !== null);
    console.log(selectedOrderIds);

    //call service
    this.pizzaSvc.createOrder(order)
    .then(result => {
      console.info('>>>> result:', result)
    })
    .catch(error=> {
      console.error("---error:", error)

      alert(`Cannot add order ${order.name}`)
    })
  }



  onCheckboxChange(e: any) {
    const checkArray: FormArray = this.orderForm.get('toppings') as FormArray;
    if (e.target.checked) {
      checkArray.push(new FormControl(e.target.value));
    } else {
      let i: number = 0;
      checkArray.controls.forEach((item) => {
        if (item.value == e.target.value) {
          checkArray.removeAt(i);
          return;
        }
        i++;
      });
    }
  }

}
