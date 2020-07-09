export class UserRequest {
    firstname: string;
    lastname: string;
    email: string;
    password: string;
    isSelected: string;  
    name : string;
    adress : string;
    number : string;
    pib : string;



    constructor (firstname:string, lastname:string, email:string, password:string, isSelected:string, name:string, adress: string, number: string, pib:string  ) {
        this.firstname=firstname;
        this.lastname=lastname;
        this.email=email;
        this.password=password;
        this.isSelected=isSelected;
        this.name=name;
        this.adress=adress;
        this.number=number;
        this.pib = pib;
    }
}