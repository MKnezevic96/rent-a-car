import { Manufacturer } from './Manufacturer';
import { CarClass } from './CarClass';
import { TransmissionType } from './TransmissionType';

export class CarModel {
        name: string;
        manufacturer: Manufacturer;
        class: CarClass;
        transmission: TransmissionType;
        deleted:boolean;
      
    }
