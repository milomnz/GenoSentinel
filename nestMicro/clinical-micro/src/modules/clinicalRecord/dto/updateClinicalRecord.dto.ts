
import { IsString, IsNotEmpty, IsNumber } from 'class-validator';
export class UpdateClinicalRecordDto {
    @IsString()
    @IsNotEmpty()
    stage: string;
    @IsString()
    @IsNotEmpty()
    treatmentProtocol: string;
}