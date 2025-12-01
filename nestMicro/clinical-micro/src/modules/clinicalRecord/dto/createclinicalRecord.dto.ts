import { IsString, IsNotEmpty, IsNumberString, IsDateString, IsArray, IsNumber } from 'class-validator';
import { ApiProperty } from '@nestjs/swagger';
import { TumorType } from 'src/modules/tumorType/entities/TumorType.entity';


export class CreateClinicalRecordDto {

  @ApiProperty({
    description: 'ID del paciente (Foreign Key)',
    example: '15'
  })

  @IsNotEmpty()
  @IsNumber()
  idPatient: number;


  @ApiProperty({
    description: 'ID del tipo de tumor (Foreign Key)',
    example: [3, 4]
  })
  @IsNotEmpty()
  @IsNumber({}, { each: true })
  idTumorType: number;


  @ApiProperty({
    description: 'Fecha del diagnostico',
    example: '2006-03-13'
  })

  @IsNotEmpty()
  @IsDateString()
  diagnosticDate: Date;



  @ApiProperty({
    description: 'Etapa clinica del tumor',
    example: 'Stage II-A'
  })
  @IsString()
  @IsNotEmpty()
  stage: string;

  /**
   * Protocolo de tratamiento
   * @example 'Cirugia'
   */
  @ApiProperty({
    description: 'Protocolo de tratamiento asignado al paciente',
    example: 'Quimioterapia + Radioterapia'
  })

  @IsString()
  @IsNotEmpty()
  treatmentProtocol: string;
}


