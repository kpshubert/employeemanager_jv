// types/Employee.ts
import type { Department } from "./Department";

export interface Employee {
  Id: number;
  FirstName: string;
  LastName: string;
  Phone: string;
  Email: string;
  Department: Department;
}
