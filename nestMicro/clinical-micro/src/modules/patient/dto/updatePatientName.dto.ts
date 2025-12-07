import { IsNotEmpty, IsNumber, IsString } from 'class-validator';
export class UpdatePatientNameDto {
  @IsString()
  @IsNotEmpty()
  firstName: string;
}