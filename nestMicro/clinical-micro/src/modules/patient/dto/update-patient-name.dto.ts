import { IsNotEmpty, IsString } from 'class-validator';
export class UpdatePatientNameDto {
  @IsString()
  @IsNotEmpty()
  name: string;
}