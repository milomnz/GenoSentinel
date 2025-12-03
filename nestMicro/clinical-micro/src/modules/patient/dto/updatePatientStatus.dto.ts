import { IsNotEmpty, IsString } from 'class-validator';
export class UpdatePatientStatusDto {
  @IsString()
  @IsNotEmpty()
  status: string;
}